package com.bach.task_flow.dtos.requests.user;

import com.bach.task_flow.validations.FieldValueMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@FieldValueMatch(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "INVALID_PASSWORD_CONFIRMATION"
)
public class AdminCreationRequest {

    @Size(min = 3, message = "USERNAME_INVALID")
    String username;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    @Size(min = 8, message = "CONFIRM_PASSWORD_INVALID")
    String confirmPassword;
    @Email(message = "EMAIL_INVALID", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    String email;

}
