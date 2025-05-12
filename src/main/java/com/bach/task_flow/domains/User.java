package com.bach.task_flow.domains;


import com.bach.task_flow.enums.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends BaseEntityAudit{

    String username;
    String password;
    String email;
    @Column(name = "avatar_url", length = 512)
    String avatarUrl = "/images/default_avatar.png";
    boolean enabled = false;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Role role = Role.USER;
    @ManyToMany(mappedBy = "members")
    Set<Project> projects = new HashSet<>();
    @OneToMany(
            mappedBy = "manager",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    Set<Project> managedProjects = new HashSet<>();
    @ManyToMany(mappedBy = "users")
    Set<Task> tasks = new HashSet<>();
    @OneToMany(mappedBy = "user")
    Set<Comment> comments = new HashSet<>();

}
