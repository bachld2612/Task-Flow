package com.bach.spring_database.controllers;

import com.bach.spring_database.dtos.ApiResponse;
import com.bach.spring_database.dtos.requests.user.AdminCreationRequest;
import com.bach.spring_database.dtos.requests.user.ChangePasswordRequest;
import com.bach.spring_database.dtos.responses.user.AdminCreationResponse;
import com.bach.spring_database.dtos.responses.user.ChangePasswordResponse;
import com.bach.spring_database.dtos.responses.user.UserResponse;
import com.bach.spring_database.services.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User APIs", description = "These APIs for user features, to use these APIs, please login to get jwt token and provide it!")
public class UserController {

    UserService userService;


    @Operation(
            summary     = "Upload User Avatar",
            description = "Accepts a multipart/form-data request containing the user's avatar image file, " +
                    "stores the image, and returns a confirmation message upon successful upload."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Avatar upload successful",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1000,
              "result": "Upload avatar successfully"
            }
            """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Unauthenticated",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1006,
              "message": "Unauthenticated"
            }
            """)
                    )
            )
    })
    @PostMapping(
            value = "/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file) {

        userService.uploadAvatar(file);
        return ApiResponse.<String>builder()
                .result("Upload avatar successfully")
                .build();

    }


    @Operation(
            summary     = "Change User Password",
            description = "Allows a user to change their password by providing the current (old) password and a new password. " +
                    "The new password is returned as a BCrypt-hashed string."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Password change successful",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1000,
              "result": {
                "oldPassword": "currentPlaintextPassword",
                "newPassword": "$2a$10$eW5X7vnQh7YhK1z4A9fEeu5Zk3L0pQ9M8R2bT6uJ1vC3xY2Z4W8a"
              }
            }
            """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Error changing password",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": "100x",
              "message": "Error Message"
            }
            """)
                    )
            )
    })
    @PostMapping("/change-password")
    public ApiResponse<ChangePasswordResponse> changePassword(@Valid ChangePasswordRequest request) {

        return ApiResponse.<ChangePasswordResponse>builder()
                .result(userService.changePassword(request))
                .build();

    }

    @Operation(
            summary     = "Delete User by Username",
            description = "Deletes the user identified by the given username and returns a confirmation message. Users can only delete their account. If you are login as ADMIN account, you can delete all accounts."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "User deletion successful",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1000,
              "result": "Delete user successfully"
            }
            """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description  = "Unauthorized",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1018,
              "message": "Unauthorized"
            }
            """)
                    )
            )
    })
    @DeleteMapping("/{username}")
    public ApiResponse<String> deleteUser(@PathVariable String username) {

        userService.deleteUser(username);
        return ApiResponse.<String>builder()
                .result("Delete user successfully")
                .build();

    }

    @Operation(
            summary     = "Create Admin User",
            description = "Creates a new administrator account using the provided details and returns the newly created admin's credentials. Only admin account can call this api"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Admin creation successful",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": 1000,
              "result": {
                "username": "admin1",
                "password": "$2a$10$27GDgdwuvH60YmImd/hLweuNvpWm.CUGJ0FYEMWsq0WczRKxvl8Li",
                "email": "admin@gmail.com",
                "role": "ADMIN",
                "enabled": true
              }
            }
            """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Bad request",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
            {
              "code": "100x",
              "message": "Error message"
            }
            """)
                    )
            )
    })
    @PostMapping("/create-admin")
    public ApiResponse<AdminCreationResponse> createAdmin(AdminCreationRequest request) {

        return ApiResponse.<AdminCreationResponse>builder()
                .result(userService.createAdmin(request))
                .build();

    }


    @Operation(
            summary     = "Get All Users",
            description = "Retrieves a paginated list of all users. Access restricted to ADMIN role."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Users retrieved successfully",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1000,
                              "result": {
                                "content": [
                                  {
                                    "id": "af2f7314-44c0-4c9a-b15f-62141472f867",
                                    "username": "bachld1",
                                    "email": "lyduybach700@gmail.com",
                                    "avatarUrl": "/images/default_avatar.png",
                                    "role": "USER",
                                    "enabled": true,
                                    "updatedAt": "2025-05-02"
                                  },
                                  {
                                    "id": "ef34941a-5d42-4457-8783-d9643c6950b5",
                                    "username": "bachld",
                                    "email": "lyduybach800@gmail.com",
                                    "avatarUrl": "https://res.cloudinary.com/dpvxx0v6y/image/upload/v1746025014/xhayiexs0ifbzgvqgenj.jpg",
                                    "role": "MANAGER",
                                    "enabled": true,
                                    "updatedAt": "2025-05-02"
                                  },
                                  {
                                    "id": "2fb1ec7f-4914-4bac-ad0f-a9f8b2eced2b",
                                    "username": "admin1",
                                    "email": "admin@gmail.com",
                                    "avatarUrl": "/images/default_avatar.png",
                                    "role": "ADMIN",
                                    "enabled": true,
                                    "updatedAt": "2025-05-01"
                                  },
                                  {
                                    "id": "f5747965-4f27-4d85-95f5-d9143709e69d",
                                    "username": "admin",
                                    "email": null,
                                    "avatarUrl": "/images/default_avatar.png",
                                    "role": "ADMIN",
                                    "enabled": true,
                                    "updatedAt": "2025-05-01"
                                  }
                                ],
                                "pageable": {
                                  "pageNumber": 0,
                                  "pageSize": 10,
                                  "sort": {
                                    "empty": false,
                                    "sorted": true,
                                    "unsorted": false
                                  },
                                  "offset": 0,
                                  "paged": true,
                                  "unpaged": false
                                },
                                "last": true,
                                "totalPages": 1,
                                "totalElements": 4,
                                "first": true,
                                "numberOfElements": 4,
                                "size": 10,
                                "number": 0,
                                "sort": {
                                  "empty": false,
                                  "sorted": true,
                                  "unsorted": false
                                },
                                "empty": false
                              }
                            }
                        """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description  = "Unauthorized",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1018,
                              "message": "Unauthorized"
                            }
                        """)
                    )
            )
    })
    @GetMapping
    public ApiResponse<Page<UserResponse>> getUsers(
            @PageableDefault(page = 0, size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        return ApiResponse.<Page<UserResponse>>builder()
                .result(userService.getAllUsers(pageable))
                .build();

    }

}
