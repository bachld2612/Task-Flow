package com.bach.spring_database.domains;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "attachments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachment extends BaseEntityAudit{

    String name;
    @Column(name = "file_url")
    String fileUrl;
    @ManyToOne
    Task task;

}
