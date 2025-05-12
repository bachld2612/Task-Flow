package com.bach.task_flow.dtos.requests.task;

import com.bach.task_flow.enums.Status;
import jakarta.persistence.Column;
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
    Status status = Status.TODO;
    LocalDate dueDate;
    UUID project_id;

}
