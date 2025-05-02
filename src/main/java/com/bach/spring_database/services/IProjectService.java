package com.bach.spring_database.services;

import com.bach.spring_database.dtos.requests.project.AddMemberToProjectRequest;
import com.bach.spring_database.dtos.requests.project.ProjectCreationRequest;
import com.bach.spring_database.dtos.responses.project.AddMemberToProjectResponse;
import com.bach.spring_database.dtos.responses.project.ProjectCreationResponse;

import java.util.UUID;

public interface IProjectService {

    ProjectCreationResponse createProject(ProjectCreationRequest request);
    AddMemberToProjectResponse addMemberToProject(AddMemberToProjectRequest request, UUID projectId);

}
