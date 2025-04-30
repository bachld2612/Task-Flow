package com.bach.spring_database.controllers;


import com.bach.spring_database.services.IEmailService;
import com.bach.spring_database.services.impl.CloudinaryService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestController {

    CloudinaryService cloudinaryService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(cloudinaryService.upload(file));

    }

}
