package com.bach.spring_database.domains;


import com.bach.spring_database.enums.Status;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task extends BaseEntityAudit{

    String title;
    @Column(columnDefinition = "TEXT")
    String description;
    Status status = Status.TODO;
    @Column(name = "due_date")
    LocalDate dueDate;
    @ManyToMany
    @JoinTable(
            name = "task_assignee",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> users = new HashSet<>();
    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    Set<Attachment> attachments = new HashSet<>();
    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    Set<Comment> comments = new HashSet<>();
    @ManyToOne(optional = false)
    Project project;

}
