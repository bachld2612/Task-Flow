package com.bach.spring_database.dtos.responses.project;


import com.bach.spring_database.domains.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddMemberToProjectResponse {

    String project_name;
    Set<String> memberNames;

}
