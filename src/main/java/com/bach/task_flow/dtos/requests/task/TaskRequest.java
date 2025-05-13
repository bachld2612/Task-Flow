package com.bach.task_flow.dtos.requests.task;

import com.bach.task_flow.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskRequest {

    String title;
    String description;
    Status status;
    LocalDate dueDate;

}
