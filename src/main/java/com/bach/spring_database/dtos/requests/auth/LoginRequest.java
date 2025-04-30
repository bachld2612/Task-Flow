package com.bach.spring_database.dtos.requests.auth;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    @Size(min = 3, message = "ACCOUNT_INVALID")
    String account;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

}
