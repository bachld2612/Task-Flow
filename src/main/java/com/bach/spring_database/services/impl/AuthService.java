package com.bach.spring_database.services.impl;


import com.bach.spring_database.domains.EmailOTP;
import com.bach.spring_database.domains.User;
import com.bach.spring_database.dtos.requests.auth.RegisterRequest;
import com.bach.spring_database.dtos.requests.email.EmailRequest;
import com.bach.spring_database.dtos.requests.email.EmailVerificationRequest;
import com.bach.spring_database.dtos.responses.auth.RegisterResponse;
import com.bach.spring_database.dtos.responses.email.EmailVerificationResponse;
import com.bach.spring_database.exceptions.ApplicationException;
import com.bach.spring_database.exceptions.ErrorCode;
import com.bach.spring_database.mappers.AuthMapper;
import com.bach.spring_database.mappers.EmailMapper;
import com.bach.spring_database.repositories.EmailOtpRepository;
import com.bach.spring_database.repositories.UserRepository;
import com.bach.spring_database.services.IAuthService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    UserRepository userRepository;
    EmailService emailService;
    AuthMapper authMapper;
    PasswordEncoder passwordEncoder;
    EmailOtpRepository emailOtpRepository;
    EmailMapper emailMapper;

    @Override
    @Transactional
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
        EmailOTP emailOTP = new EmailOTP();
        User user = userRepository.save(authMapper.toUser(request));
        return authMapper.toResponse(user);

    }

    @Override
    @Transactional
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
    @Transactional
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
}
