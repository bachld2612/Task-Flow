package com.bach.task_flow.services.impl;


import com.bach.task_flow.domains.EmailOTP;
import com.bach.task_flow.domains.InvalidateToken;
import com.bach.task_flow.domains.User;
import com.bach.task_flow.dtos.requests.auth.IntrospectRequest;
import com.bach.task_flow.dtos.requests.auth.LoginRequest;
import com.bach.task_flow.dtos.requests.auth.LogoutRequest;
import com.bach.task_flow.dtos.requests.auth.RegisterRequest;
import com.bach.task_flow.dtos.requests.email.EmailRequest;
import com.bach.task_flow.dtos.requests.email.EmailVerificationRequest;
import com.bach.task_flow.dtos.responses.auth.InfoResponse;
import com.bach.task_flow.dtos.responses.auth.IntrospectResponse;
import com.bach.task_flow.dtos.responses.auth.LoginResponse;
import com.bach.task_flow.dtos.responses.auth.RegisterResponse;
import com.bach.task_flow.dtos.responses.email.EmailVerificationResponse;
import com.bach.task_flow.exceptions.ApplicationException;
import com.bach.task_flow.exceptions.ErrorCode;
import com.bach.task_flow.mappers.AuthMapper;
import com.bach.task_flow.mappers.EmailMapper;
import com.bach.task_flow.repositories.EmailOtpRepository;
import com.bach.task_flow.repositories.InvalidateTokenRepository;
import com.bach.task_flow.repositories.UserRepository;
import com.bach.task_flow.security.RefreshToken;
import com.bach.task_flow.security.utils.RefreshTokenUtils;
import com.bach.task_flow.services.EmailService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements com.bach.task_flow.services.AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    UserRepository userRepository;
    EmailService emailService;
    AuthMapper authMapper;
    PasswordEncoder passwordEncoder;
    EmailOtpRepository emailOtpRepository;
    EmailMapper emailMapper;
    InvalidateTokenRepository tokenRepository;
    RedisTemplate<String, Object> redisTemplate;

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @Value("${jwt.refreshTokenExpirationMs}")
    @NonFinal
    Long REFRESH_TOKEN_EXPIRATION_MS;

    @Override
    public RegisterResponse register(RegisterRequest request) {

        Optional<User> existingUsername = userRepository.findByUsername(request.getUsername());
        if (existingUsername.isPresent()) {
            throw new ApplicationException(ErrorCode.EXISTED_USERNAME);
        }
        Optional<User> existingEmail = userRepository.findByEmail(request.getEmail());
        if (existingEmail.isPresent()) {
            throw new ApplicationException(ErrorCode.EXISTED_EMAIL);
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = userRepository.save(authMapper.toUser(request));
        return authMapper.toResponse(user);

    }

    @Override
    public EmailVerificationResponse activateAccount(EmailVerificationRequest request) {

        EmailOTP emailOTP = emailOtpRepository.findByEmailAndOtp(request.getEmail(),request.getOtp())
                .orElseThrow(() -> new ApplicationException(ErrorCode.OTP_INVALID));
        if (emailOTP.getExpiredAt().isBefore(Instant.now())) {
            throw new ApplicationException(ErrorCode.OTP_EXPIRED);
        }
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        user.setEnabled(true);
        userRepository.save(user);
        emailOtpRepository.delete(emailOTP);
        return EmailVerificationResponse.builder()
                .verified(true)
                .build();

    }

    @Override
    public void generateAndSendEmail(String email) {

        emailOtpRepository.deleteByEmail(email);
        String code = String.format("%06d", new Random().nextInt(999999));
        Instant now = Instant.now();
        Instant expiredAt = now.plus(5, ChronoUnit.MINUTES);
        EmailRequest emailRequest = EmailRequest.builder()
                .email(email)
                .expiredAt(expiredAt)
                .otp(code)
                .build();
        emailOtpRepository.save(emailMapper.toEmailOTP(emailRequest));
        emailService.sendOTP(email, code);

    }

    @Override
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {

        User user = userRepository.findByUsernameOrEmail(request.getAccount(),request.getAccount())
                .orElseThrow(() -> new ApplicationException(ErrorCode.ACCOUNT_NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_INCORRECT);
        }

        if(!user.isEnabled()){
            throw new ApplicationException(ErrorCode.ACCOUNT_NOT_ACTIVATED);
        }

        saveRefreshToken(user.getId(), response);

        return LoginResponse.builder()
                .token(generateToken(user))
                .build();

    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {

        boolean isValid = true;
        try {
            verifyToken(request.getToken());
        }catch (Exception e){
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();

    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public InfoResponse getMyInfo() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        return authMapper.toInfoResponse(user);
    }

    @Override
    public void logout(LogoutRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws ParseException, JOSEException {

        SignedJWT signedJWT = verifyToken(request.getToken());
        String tokenId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        InvalidateToken invalidateToken = InvalidateToken.builder()
                .tokenId(tokenId)
                .expiryTime(expiration)
                .build();
        tokenRepository.save(invalidateToken);
        String refreshToken = RefreshTokenUtils.getRefreshTokenFromCookie(httpServletRequest);
        if(refreshToken != null && !refreshToken.isBlank()) {
            String key = "refreshToken:" + refreshToken;
            redisTemplate.delete(key);
        }
        RefreshTokenUtils.clearRefreshTokenCookie(response);

    }

    @Override
    public LoginResponse refreshToken(HttpServletRequest httpServletRequest) {
        String refreshToken = RefreshTokenUtils.getRefreshTokenFromCookie(httpServletRequest);
        String key =  "refreshToken:" + refreshToken;
        RefreshToken token = (RefreshToken) redisTemplate.opsForValue().get(key);
        if(refreshToken == null || refreshToken.isBlank() || Objects.requireNonNull(token).getExpirationTime().isBefore(Instant.now())) {
            throw new ApplicationException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        User user = userRepository.findById(token.getUserId()).orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        return LoginResponse.builder()
                .token(generateToken(user))
                .build();
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        boolean verified = signedJWT.verify(verifier);
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        if(!(verified && expiration.after(new Date()))){
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }
        if (tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new ApplicationException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;

    }

    private String generateToken(User request){

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(request.getUsername())
                .issuer("bachld")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(3, ChronoUnit.HOURS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", request.getRole())
                .build();
        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        }catch (JOSEException e) {
            log.error("Cannot generate token", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private void saveRefreshToken(UUID userId, HttpServletResponse response) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .userId(userId)
                .expirationTime(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION_MS))
                .build();
        String key = "refreshToken:"+refreshToken.getToken();
        redisTemplate.opsForValue().set(key, refreshToken);
        redisTemplate.expire(key, refreshToken.getExpirationTime().toEpochMilli() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        RefreshTokenUtils.setRefreshTokenCookie(response, refreshToken.getToken(), REFRESH_TOKEN_EXPIRATION_MS);

    }

}
