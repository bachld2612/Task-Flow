package com.bach.task_flow.dtos.responses.task;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserToTaskResponse {

    UUID id;
    String title;
    Set<String> usernames;

}
