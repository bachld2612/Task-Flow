package com.bach.task_flow.dtos.requests.task;

import com.bach.task_flow.enums.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskCreationRequest {

    String title;
    String description;
    Status status;
    LocalDate dueDate;
    @NotNull(message = "PROJECT_NOT_NULL")
    UUID project_id;

}
