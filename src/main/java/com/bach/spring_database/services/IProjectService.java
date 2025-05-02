package com.bach.spring_database.services;

import com.bach.spring_database.dtos.requests.project.AddMemberToProjectRequest;
import com.bach.spring_database.dtos.requests.project.ProjectCreationRequest;
import com.bach.spring_database.dtos.responses.project.AddMemberToProjectResponse;
import com.bach.spring_database.dtos.responses.project.ProjectCreationResponse;
import com.bach.spring_database.dtos.responses.project.ProjectResponse;
import com.bach.spring_database.dtos.responses.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IProjectService {

    ProjectCreationResponse createProject(ProjectCreationRequest request);
    AddMemberToProjectResponse addMemberToProject(AddMemberToProjectRequest request, UUID projectId);
    UserResponse getProjectManager(UUID projectId);
    Page<UserResponse> getProjectMembers(UUID projectId, Pageable pageable);
    Page<ProjectResponse> getProjects(Pageable pageable);
    void deleteProject(UUID projectId);

}
