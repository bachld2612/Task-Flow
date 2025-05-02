package com.bach.spring_database.dtos.responses.project;

import com.bach.spring_database.domains.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProjectCreationResponse {

    String name;
    String description;
    User manager;

}
