package com.bach.task_flow.dtos.responses.email;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailResponse {

    String email;
    String otp;
    Instant expiredAt;

}
