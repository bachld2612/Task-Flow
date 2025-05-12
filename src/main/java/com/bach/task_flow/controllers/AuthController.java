package com.bach.task_flow.controllers;


import com.bach.task_flow.dtos.ApiResponse;
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
import com.bach.task_flow.services.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication and Registration APIs", description = "These API for authentication and registration features")
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
    @PostMapping("/activate-account")
    public ApiResponse<EmailVerificationResponse> activateAccount(EmailVerificationRequest request){

        return ApiResponse.<EmailVerificationResponse>builder()
                .result(authService.activateAccount(request))
                .build();

    }


    @Operation(
            summary     = "User Login",
            description = "Authenticates a user with provided credentials (username or email and password) and returns a JWT access token if successful."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Login successful",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1000,
              "result": {
                "token": "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJiYWNobGQiLCJzdWIiOiJiYWNobGQiLCJleHAiOjE3NDYwMTU4MjMsImlhdCI6MTc0NjAwNTAyMywianRpIjoiODRmNDQzNjEtMTVjMy00NGViLWI2MDEtZmQzZWQzNjNmYzBkIiwic2NvcGUiOiJVU0VSIn0.-s8OdXAMMG5xS-P6FD54BajkRRk4ZXSTzvp_kl4tXr7QDnZxRt45QvfjEldvwtX9G_i83IsoaLMKnjx62JcgHA"
              }
            }
            """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Invalid credentials or request",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": "10xx",
              "message": "Error Message"
            }
            """)
                    )
            )
    })
    @PostMapping("/token")
    public ApiResponse<LoginResponse> login(@Valid LoginRequest loginRequest){

        return ApiResponse.<LoginResponse>builder()
                .result(authService.login(loginRequest))
                .build();

    }

    @Operation(
            summary     = "Introspect Access Token",
            description = "Checks whether the provided access token is still valid. " +
                    "Returns a JSON payload with a `valid` flag: `true` if the token is active, `false` otherwise."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Token introspection result",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1000,
              "result": {
                "valid": false
              }
            }
            """)
                    )
            )
    })
    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@Valid IntrospectRequest request){

        return ApiResponse.<IntrospectResponse>builder()
                .result(authService.introspect(request))
                .build();

    }

    @Operation(
            summary     = "User Logout",
            description = "Invalidates the user's current session or token and logs the user out of the system. " +
                    "Returns a confirmation message upon successful logout."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Logout successful",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1000,
              "result": "Logout Successfully"
            }
            """)
                    )
            )
    })
    @PostMapping("/logout")
    public ApiResponse<String> logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {

        authService.logout(logoutRequest);
        return ApiResponse.<String>builder()
                .result("Logout Successfully")
                .build();

    }

    @GetMapping("/my-info")
    public ApiResponse<InfoResponse> getMyInfo(){

        return ApiResponse.<InfoResponse>builder()
                .result(authService.getMyInfo())
                .build();

    }


}
