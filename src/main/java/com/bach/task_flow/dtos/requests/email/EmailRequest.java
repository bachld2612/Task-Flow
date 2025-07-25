package com.bach.task_flow.dtos.requests.email;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailRequest {

    String email;
    String otp;
    Instant expiredAt;

}
