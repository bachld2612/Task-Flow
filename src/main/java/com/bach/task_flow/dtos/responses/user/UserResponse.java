package com.bach.task_flow.dtos.responses.user;

import com.bach.task_flow.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    UUID id;
    String username;
    String email;
    String avatarUrl;
    Role role;
    boolean enabled;
    LocalDate updatedAt;


}
