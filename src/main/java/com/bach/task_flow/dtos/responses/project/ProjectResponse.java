package com.bach.task_flow.dtos.responses.project;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectResponse {

    UUID id;
    String name;
    String description;
    LocalDate updatedAt;


}
