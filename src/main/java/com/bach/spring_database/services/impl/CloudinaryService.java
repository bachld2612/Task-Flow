package com.bach.spring_database.services.impl;


import com.bach.spring_database.services.ICloudinaryService;
import com.cloudinary.Cloudinary;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {

    Cloudinary cloudinary;

    public String upload(MultipartFile file) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), Map.of());
            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
