package com.bach.spring_database.services;

import com.bach.spring_database.dtos.requests.user.ChangePasswordRequest;
import com.bach.spring_database.dtos.responses.user.ChangePasswordResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

    void uploadAvatar(MultipartFile file);
    ChangePasswordResponse changePassword(ChangePasswordRequest request);

}
