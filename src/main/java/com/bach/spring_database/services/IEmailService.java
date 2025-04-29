package com.bach.spring_database.services;

import com.bach.spring_database.dtos.requests.email.EmailVerificationRequest;
import com.bach.spring_database.dtos.responses.email.EmailVerificationResponse;

public interface IEmailService {

    void sendOTP(String mail, String otp);

}
