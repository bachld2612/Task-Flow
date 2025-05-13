package com.bach.task_flow.dtos.requests.task;

import com.bach.task_flow.enums.Status;
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
public class TaskUpdateRequest {

    String title;
    String description;
    Status status;
    LocalDate dueDate;

}
