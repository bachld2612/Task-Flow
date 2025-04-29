package com.bach.spring_database.dtos.responses.email;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailVerificationResponse {

    boolean verified;

}
