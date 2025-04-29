package com.bach.spring_database.controllers;


import com.bach.spring_database.dtos.ApiResponse;
import com.bach.spring_database.dtos.requests.auth.RegisterRequest;
import com.bach.spring_database.dtos.requests.email.EmailVerificationRequest;
import com.bach.spring_database.dtos.responses.auth.RegisterResponse;
import com.bach.spring_database.dtos.responses.email.EmailVerificationResponse;
import com.bach.spring_database.services.impl.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication and Registration API", description = "This API for authentication and registration feature")
public class AuthController {

    AuthService authService;

    @Operation(
            summary     = "User Registration",
            description = "Registers a new user by accepting registration details (username, email, password), " +
                    "validates the input, creates the user account and returns a RegisterResponse " +
                    "containing the newly created user's ID and registration status."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Registration successful",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                {
                  "code": 1000,
                  "result": {
                    "id": "2203fe89-035e-4217-83e3-253811d4c60c",
                    "username": "bachld1",
                    "password": "$2a$10$N5ZninP9faLWDBx/.rd70OC9DQu25QDzbb7lwwf6uvLkF0KAzBIGq",
                    "email": "lyduybach700@gmail.com",
                    "role": "USER",
                    "enabled": false
                  }
                }
                """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Existed Username or email & Fields is not validated",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                {
                  "code": "100x",
                  "message": "Username or email has been existed &  Fields is not validated"
                }
                """)
                    )
            )
    })
    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@Valid RegisterRequest registerRequest) {

        return ApiResponse.<RegisterResponse>builder()
                .result(authService.register(registerRequest))
                .build();

    }

    @Operation(
            summary = "Resend Otp to verify",
            description = "This API for resending OTP used for verification account",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Resend OTP successfully",
                            content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(example = """
                                    {
                                      "code": "1000",
                                      "result": "Send Otp Successfully"
                                    }
                                    """)
                            )
                    )
            }
    )
    @PostMapping("/otp")
    public ApiResponse<String> resendOtp(String email){

        authService.generateAndSendEmail(email);
        return ApiResponse.<String>builder()
                .result("Send Otp Successfully")
                .build();

    }

    @Operation(
            summary     = "Verify Email OTP",
            description = "Verifies a user's email address by validating the one-time password (OTP) sent during registration. " +
                    "Returns a flag indicating whether the email was successfully verified."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Email verification successful",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(
                                    example = """
            {
              "code": 1000,
              "result": {
                "verified": true
              }
            }
            """
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Invalid or expired OTP",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(
                                    example = """
            {
              "code": "100x",
              "message": "OTP is invalid or has expired"
            }
            """
                            )
                    )
            )
    })
    @PostMapping("/verifyEmail")
    public ApiResponse<EmailVerificationResponse> verifyEmail(EmailVerificationRequest request){

        return ApiResponse.<EmailVerificationResponse>builder()
                .result(authService.verifyEmail(request))
                .build();

    }



}
