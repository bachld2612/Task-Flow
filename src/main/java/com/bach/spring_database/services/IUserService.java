package com.bach.spring_database.services;

import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

    void uploadAvatar(MultipartFile file);

}
