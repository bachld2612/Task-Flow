package com.bach.task_flow.services;

import com.bach.task_flow.dtos.requests.user.AdminCreationRequest;
import com.bach.task_flow.dtos.requests.user.ChangePasswordRequest;
import com.bach.task_flow.dtos.responses.user.AdminCreationResponse;
import com.bach.task_flow.dtos.responses.user.ChangePasswordResponse;
import com.bach.task_flow.dtos.responses.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {

    void uploadAvatar(MultipartFile file);
    ChangePasswordResponse changePassword(ChangePasswordRequest request);
    void deleteUser(String username);
    AdminCreationResponse createAdmin(AdminCreationRequest request);
    Page<UserResponse> getAllUsers(Pageable pageable);

}
