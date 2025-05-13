package com.bach.task_flow.dtos.requests.task;

import com.bach.task_flow.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusUpdateRequest {

    Status status;

}
