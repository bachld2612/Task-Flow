package com.bach.task_flow.dtos.responses.project;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectCreationResponse {

    String name;
    String description;
    String managerName;

}
