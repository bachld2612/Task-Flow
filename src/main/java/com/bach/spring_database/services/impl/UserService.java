package com.bach.spring_database.services.impl;

import com.bach.spring_database.domains.User;
import com.bach.spring_database.dtos.requests.user.ChangePasswordRequest;
import com.bach.spring_database.dtos.responses.user.ChangePasswordResponse;
import com.bach.spring_database.exceptions.ApplicationException;
import com.bach.spring_database.exceptions.ErrorCode;
import com.bach.spring_database.repositories.UserRepository;
import com.bach.spring_database.services.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    UserRepository userRepository;
    CloudinaryService cloudinaryService;
    PasswordEncoder passwordEncoder;


    @Override
    @PreAuthorize("isAuthenticated()")
    public void uploadAvatar(MultipartFile file) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        String imgUrl = cloudinaryService.upload(file);
        user.setAvatarUrl(imgUrl);
        userRepository.save(user);

    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ApplicationException(ErrorCode.PASSWORD_INCORRECT);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return ChangePasswordResponse.builder()
                .oldPassword(request.getOldPassword())
                .newPassword(user.getPassword())
                .build();

    }
}
