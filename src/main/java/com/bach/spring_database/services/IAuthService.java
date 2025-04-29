package com.bach.spring_database.services;

import com.bach.spring_database.dtos.requests.auth.RegisterRequest;
import com.bach.spring_database.dtos.requests.email.EmailRequest;
import com.bach.spring_database.dtos.requests.email.EmailVerificationRequest;
import com.bach.spring_database.dtos.responses.auth.RegisterResponse;
import com.bach.spring_database.dtos.responses.email.EmailVerificationResponse;

public interface IAuthService {

    RegisterResponse register(RegisterRequest request);
    EmailVerificationResponse verifyEmail(EmailVerificationRequest request);
    void generateAndSendEmail(String email);

}
