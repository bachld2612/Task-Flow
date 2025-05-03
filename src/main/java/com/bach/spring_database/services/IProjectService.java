package com.bach.spring_database.services;

import com.bach.spring_database.domains.Project;
import com.bach.spring_database.domains.User;
import com.bach.spring_database.dtos.requests.project.AddMemberToProjectRequest;
import com.bach.spring_database.dtos.requests.project.DeleteMemberFromProjectRequest;
import com.bach.spring_database.dtos.requests.project.ProjectCreationRequest;
import com.bach.spring_database.dtos.responses.project.AddMemberToProjectResponse;
import com.bach.spring_database.dtos.responses.project.DeleteMemberFromProjectResponse;
import com.bach.spring_database.dtos.responses.project.ProjectCreationResponse;
import com.bach.spring_database.dtos.responses.project.ProjectResponse;
import com.bach.spring_database.dtos.responses.user.UserResponse;
import com.bach.spring_database.enums.Role;
import com.bach.spring_database.exceptions.ApplicationException;
import com.bach.spring_database.exceptions.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.UUID;

public interface IProjectService {

    ProjectCreationResponse createProject(ProjectCreationRequest request);
    AddMemberToProjectResponse addMemberToProject(AddMemberToProjectRequest request, UUID projectId);
    UserResponse getProjectManager(UUID projectId);
    Page<UserResponse> getProjectMembers(UUID projectId, Pageable pageable);
    Page<ProjectResponse> getProjects(Pageable pageable);
    void deleteProject(UUID projectId);
    DeleteMemberFromProjectResponse deleteMemberFromProject(DeleteMemberFromProjectRequest request, UUID projectId);


}
