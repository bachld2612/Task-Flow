package com.bach.task_flow.services;

import com.bach.task_flow.dtos.requests.project.AddMemberToProjectRequest;
import com.bach.task_flow.dtos.requests.project.DeleteMemberFromProjectRequest;
import com.bach.task_flow.dtos.requests.project.ProjectCreationRequest;
import com.bach.task_flow.dtos.responses.project.AddMemberToProjectResponse;
import com.bach.task_flow.dtos.responses.project.DeleteMemberFromProjectResponse;
import com.bach.task_flow.dtos.responses.project.ProjectCreationResponse;
import com.bach.task_flow.dtos.responses.project.ProjectResponse;
import com.bach.task_flow.dtos.responses.task.TaskResponse;
import com.bach.task_flow.dtos.responses.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProjectService {

    ProjectCreationResponse createProject(ProjectCreationRequest request);
    AddMemberToProjectResponse addMemberToProject(AddMemberToProjectRequest request, UUID projectId);
    UserResponse getProjectManager(UUID projectId);
    Page<UserResponse> getProjectMembers(UUID projectId, Pageable pageable);
    Page<ProjectResponse> getProjects(Pageable pageable);
    void deleteProject(UUID projectId);
    DeleteMemberFromProjectResponse deleteMemberFromProject(DeleteMemberFromProjectRequest request, UUID projectId);
    Page<TaskResponse> getAllTasksInProject(UUID projectId, Pageable pageable);


}
