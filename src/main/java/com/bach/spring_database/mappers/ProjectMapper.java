package com.bach.spring_database.mappers;

import com.bach.spring_database.domains.Project;
import com.bach.spring_database.domains.User;
import com.bach.spring_database.dtos.requests.project.ProjectCreationRequest;
import com.bach.spring_database.dtos.responses.project.AddMemberToProjectResponse;
import com.bach.spring_database.dtos.responses.project.DeleteMemberFromProjectResponse;
import com.bach.spring_database.dtos.responses.project.ProjectCreationResponse;
import com.bach.spring_database.dtos.responses.project.ProjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ProjectMapper {

    Project toProject(ProjectCreationRequest request);

    @Mapping(target = "managerName", source = "manager")
    ProjectCreationResponse toProjectCreationResponse(Project project);

    @Mapping(target = "projectName", source = "name")
    @Mapping(target = "memberNames", source = "members")
    AddMemberToProjectResponse toAddMemberToProjectResponse(Project project);

    @Mapping(target = "projectName", source = "name")
    @Mapping(target = "memberNames", source = "members")
    DeleteMemberFromProjectResponse toDeleteMemberFromProjectResponse(Project project);

    ProjectResponse toProjectResponse(Project project);

    default String map(User user) {
        return user.getUsername();
    }

}
