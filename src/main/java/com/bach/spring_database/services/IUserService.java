package com.bach.spring_database.services;

import com.bach.spring_database.dtos.requests.user.AdminCreationRequest;
import com.bach.spring_database.dtos.requests.user.ChangePasswordRequest;
import com.bach.spring_database.dtos.responses.user.AdminCreationResponse;
import com.bach.spring_database.dtos.responses.user.ChangePasswordResponse;
import com.bach.spring_database.dtos.responses.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface IUserService {

    void uploadAvatar(MultipartFile file);
    ChangePasswordResponse changePassword(ChangePasswordRequest request);
    void deleteUser(String username);
    AdminCreationResponse createAdmin(AdminCreationRequest request);
    Page<UserResponse> getAllUsers(Pageable pageable);

}
