package com.bach.task_flow.domains;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Project extends BaseEntityAudit{

    String name;
    @Column(columnDefinition = "TEXT")
    String description;
    @ManyToOne(optional = false)
    @JoinColumn(name = "manager_id")
    User manager;
    @ManyToMany
    @JoinTable(
            name = "project_member",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    Set<User> members = new HashSet<>();
    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    Set<Task> tasks = new HashSet<>();

}
