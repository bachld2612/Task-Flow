package com.bach.spring_database.domains;


import com.bach.spring_database.enums.Status;
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
public class Task extends BaseEntityAudit{

    String title;
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
    @OneToMany(mappedBy = "task")
    Set<Attachment> attachments = new HashSet<>();
    @OneToMany(mappedBy = "task")
    Set<Comment> comments = new HashSet<>();

}
