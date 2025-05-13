package com.bach.task_flow.dtos.responses.task;

import com.bach.task_flow.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusUpdateResponse {

    String title;
    String description;
    Status status;
    LocalDate dueDate;

}
