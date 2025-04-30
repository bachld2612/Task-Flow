package com.bach.spring_database.services;

import com.bach.spring_database.dtos.requests.auth.IntrospectRequest;
import com.bach.spring_database.dtos.requests.auth.LoginRequest;
import com.bach.spring_database.dtos.requests.auth.LogoutRequest;
import com.bach.spring_database.dtos.requests.auth.RegisterRequest;
import com.bach.spring_database.dtos.requests.email.EmailVerificationRequest;
import com.bach.spring_database.dtos.responses.auth.IntrospectResponse;
import com.bach.spring_database.dtos.responses.auth.LoginResponse;
import com.bach.spring_database.dtos.responses.auth.RegisterResponse;
import com.bach.spring_database.dtos.responses.email.EmailVerificationResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthService {

    RegisterResponse register(RegisterRequest request);
    EmailVerificationResponse activateAccount(EmailVerificationRequest request);
    void generateAndSendEmail(String email);
    LoginResponse login(LoginRequest request);
    IntrospectResponse introspect(IntrospectRequest token);
    void logout(LogoutRequest request) throws ParseException, JOSEException;

}
