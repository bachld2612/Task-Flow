package com.bach.spring_database.controllers;

import com.bach.spring_database.dtos.ApiResponse;
import com.bach.spring_database.dtos.requests.user.ChangePasswordRequest;
import com.bach.spring_database.dtos.responses.user.ChangePasswordResponse;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User API", description = "This API for user feature, to use this api group, please login to get jwt token and provide it!")
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

    @PostMapping("/change-password")
    public ApiResponse<ChangePasswordResponse> changePassword(@Valid ChangePasswordRequest request) {

        return ApiResponse.<ChangePasswordResponse>builder()
                .result(userService.changePassword(request))
                .build();

    }

}
