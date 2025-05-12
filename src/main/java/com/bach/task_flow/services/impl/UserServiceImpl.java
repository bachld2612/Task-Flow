package com.bach.task_flow.services.impl;

import com.bach.task_flow.domains.User;
import com.bach.task_flow.dtos.requests.user.AdminCreationRequest;
import com.bach.task_flow.dtos.requests.user.ChangePasswordRequest;
import com.bach.task_flow.dtos.responses.user.AdminCreationResponse;
import com.bach.task_flow.dtos.responses.user.ChangePasswordResponse;
import com.bach.task_flow.dtos.responses.user.UserResponse;
import com.bach.task_flow.enums.Role;
import com.bach.task_flow.exceptions.ApplicationException;
import com.bach.task_flow.exceptions.ErrorCode;
import com.bach.task_flow.mappers.UserMapper;
import com.bach.task_flow.repositories.UserRepository;
import com.bach.task_flow.services.CloudinaryService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements com.bach.task_flow.services.UserService {

    UserRepository userRepository;
    CloudinaryService cloudinaryService;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;


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

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or #username == authentication.name")
    @Override
    public void deleteUser(String username) {

        userRepository.deleteByUsername(username);

    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Override
    public AdminCreationResponse createAdmin(AdminCreationRequest request) {

        Optional<User> existingUsername = userRepository.findByUsername(request.getUsername());
        if(existingUsername.isPresent()) {
            throw new ApplicationException(ErrorCode.EXISTED_USERNAME);
        }

        Optional<User> existingEmail = userRepository.findByEmail(request.getEmail());
        if(existingEmail.isPresent()) {
            throw new ApplicationException(ErrorCode.EXISTED_EMAIL);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        return userMapper.toAdminCreationResponse(user);

    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {

        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserResponse);

    }
}
