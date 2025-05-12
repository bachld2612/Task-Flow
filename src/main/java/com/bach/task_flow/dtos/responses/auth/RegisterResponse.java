package com.bach.task_flow.dtos.responses.auth;

import com.bach.task_flow.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterResponse {

    String username;
    String password;
    String email;
    Role role;
    boolean enabled;

}
