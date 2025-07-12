package com.bach.task_flow.mappers;

import com.bach.task_flow.domains.Project;
import com.bach.task_flow.domains.User;
import com.bach.task_flow.dtos.requests.project.ProjectCreationRequest;
import com.bach.task_flow.dtos.responses.project.AddMemberToProjectResponse;
import com.bach.task_flow.dtos.responses.project.DeleteMemberFromProjectResponse;
import com.bach.task_flow.dtos.responses.project.ProjectCreationResponse;
import com.bach.task_flow.dtos.responses.project.ProjectResponse;
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

    @Mapping(target = "memberNames", source = "members")
    AddMemberToProjectResponse toAddMemberToProjectResponse(Project project);

    @Mapping(target = "memberNames", source = "members")
    DeleteMemberFromProjectResponse toDeleteMemberFromProjectResponse(Project project);

    ProjectResponse toProjectResponse(Project project);

    default String map(User user) {
        return user.getUsername();
    }

}
