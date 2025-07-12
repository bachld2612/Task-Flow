package com.bach.task_flow.services;

import com.bach.task_flow.dtos.requests.auth.IntrospectRequest;
import com.bach.task_flow.dtos.requests.auth.LoginRequest;
import com.bach.task_flow.dtos.requests.auth.LogoutRequest;
import com.bach.task_flow.dtos.requests.auth.RegisterRequest;
import com.bach.task_flow.dtos.requests.email.EmailVerificationRequest;
import com.bach.task_flow.dtos.responses.auth.InfoResponse;
import com.bach.task_flow.dtos.responses.auth.IntrospectResponse;
import com.bach.task_flow.dtos.responses.auth.LoginResponse;
import com.bach.task_flow.dtos.responses.auth.RegisterResponse;
import com.bach.task_flow.dtos.responses.email.EmailVerificationResponse;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);
    EmailVerificationResponse activateAccount(EmailVerificationRequest request);
    void generateAndSendEmail(String email);
    LoginResponse login(LoginRequest request, HttpServletResponse response);
    IntrospectResponse introspect(IntrospectRequest token);
    void logout(LogoutRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response) throws ParseException, JOSEException;
    InfoResponse getMyInfo();
    LoginResponse refreshToken(HttpServletRequest request);

}
