package com.bach.task_flow.dtos.requests.project;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteMemberFromProjectRequest {

    Set<String> usernames;

}
