package com.bach.spring_database.dtos.responses.project;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteMemberFromProjectResponse {

    String projectName;
    Set<String> memberNames;

}
