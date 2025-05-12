package com.bach.task_flow.dtos.responses.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoResponse {

    UUID id;
    String username;
    String email;
    String avatarUrl;

}
