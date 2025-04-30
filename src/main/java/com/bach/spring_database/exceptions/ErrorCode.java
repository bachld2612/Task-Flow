package com.bach.spring_database.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {

    EXISTED_USERNAME(1001, "Username has been existed", HttpStatus.BAD_REQUEST),
    EXISTED_EMAIL(1002, "Email has been existed", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED(1003, "OTP has been expired", HttpStatus.BAD_REQUEST),
    OTP_INVALID(1004, "OTP is invalid", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    USERNAME_INVALID(1007, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1008, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1009, "Email is invalid", HttpStatus.BAD_REQUEST),
    INVALID_VALIDATION(1003,"Invalid Validation", HttpStatus.BAD_REQUEST),
    ACCOUNT_INVALID(1004, "Account must be username or email", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND(1005, "Account not found", HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT(1006, "Password is incorrect", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVATED(1007, "Account is not activated", HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;

}
