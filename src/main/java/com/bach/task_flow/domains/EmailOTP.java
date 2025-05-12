package com.bach.task_flow.domains;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "email_otps")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailOTP extends BaseEntity{

    String email;
    String otp;
    @Column(name = "created_at")
    @CreationTimestamp
    Instant createdAt;
    @Column(name = "expired_at")
    Instant expiredAt;

}
