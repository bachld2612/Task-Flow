package com.bach.task_flow.exceptions;

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
    INVALID_VALIDATION(1010,"Invalid Validation", HttpStatus.BAD_REQUEST),
    ACCOUNT_INVALID(1011, "Account must be username or email", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_FOUND(1012, "Account not found", HttpStatus.NOT_FOUND),
    PASSWORD_INCORRECT(1013, "Password is incorrect", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVATED(1014, "Account is not activated", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_CONFIRMATION(1015, "Confirm password must be the same as new password", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_INVALID(1016, "New password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_INVALID(1017, "Confirm password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1018, "Unauthorized", HttpStatus.UNAUTHORIZED),
    PROJECT_NOT_FOUND(1019, "Project not found", HttpStatus.NOT_FOUND),
    PROJECT_ACCESS_DENIED(1020, "Project access denied", HttpStatus.UNAUTHORIZED),
    NO_USERNAME(1021, "You must add at least one user", HttpStatus.BAD_REQUEST),
    PROJECT_NOT_NULL(1022, "Project must not be null", HttpStatus.BAD_REQUEST),
    INVALID_DUE_DATE(1023, "Due date must be after today", HttpStatus.BAD_REQUEST),
    TASK_NOT_FOUND(1024, "Task not found", HttpStatus.NOT_FOUND),
    TASK_ACCESS_DENIED(1025, "Task access denied", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(1026, "Refresh token is invalid", HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatusCode httpStatusCode;

}
