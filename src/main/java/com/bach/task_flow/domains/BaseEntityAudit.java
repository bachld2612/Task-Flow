package com.bach.task_flow.domains;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntityAudit  extends BaseEntity {

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    LocalDate createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    LocalDate updatedAt;

}
