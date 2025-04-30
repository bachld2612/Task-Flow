package com.bach.spring_database.services.impl;

import com.bach.spring_database.domains.User;
import com.bach.spring_database.repositories.UserRepository;
import com.bach.spring_database.services.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    UserRepository userRepository;
    CloudinaryService cloudinaryService;


    @Override
    @PreAuthorize("isAuthenticated()")
    public void uploadAvatar(MultipartFile file) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        String imgUrl = cloudinaryService.upload(file);
        user.setAvatarUrl(imgUrl);
        userRepository.save(user);

    }
}
