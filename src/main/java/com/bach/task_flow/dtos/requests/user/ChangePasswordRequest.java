package com.bach.task_flow.dtos.requests.user;

import com.bach.task_flow.validations.FieldValueMatch;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@FieldValueMatch(
        field = "newPassword",
        fieldMatch = "confirmPassword",
        message = "INVALID_PASSWORD_CONFIRMATION"
)

public class ChangePasswordRequest {

    @Size(min = 8, message = "PASSWORD_INVALID")
    String oldPassword;
    @Size(min = 8, message = "NEW_PASSWORD_INVALID")
    String newPassword;
    @Size(min = 8, message = "CONFIRM_PASSWORD_INVALID")
    String confirmPassword;

}
