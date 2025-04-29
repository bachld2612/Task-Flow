package com.bach.spring_database.dtos.responses.auth;

import com.bach.spring_database.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {

    UUID id;
    String username;
    String password;
    String email;
    Role role;
    boolean enabled;

}
