package com.bach.task_flow.dtos.requests.email;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailVerificationRequest {

    String email;
    String otp;

}
