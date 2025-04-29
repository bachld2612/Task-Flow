package com.bach.spring_database.domains;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends BaseEntityAudit{

    @Column(columnDefinition = "TEXT")
    String content;
    @ManyToOne
    User user;
    @ManyToOne
    Task task;

}
