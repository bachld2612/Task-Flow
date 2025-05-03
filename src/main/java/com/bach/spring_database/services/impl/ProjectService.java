package com.bach.spring_database.services.impl;

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
import com.bach.spring_database.mappers.ProjectMapper;
import com.bach.spring_database.mappers.UserMapper;
import com.bach.spring_database.repositories.ProjectRepository;
import com.bach.spring_database.repositories.UserRepository;
import com.bach.spring_database.services.IProjectService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;
    UserMapper userMapper;

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @Override
    public ProjectCreationResponse createProject(ProjectCreationRequest request) {

        //change user to manager when create project
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Project project = projectMapper.toProject(request);
        User manager = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        if(manager.getRole() == Role.USER) {
            manager.setRole(Role.MANAGER);
        }
        manager.getManagedProjects().add(project);
        project.setManager(manager);

        return projectMapper.toProjectCreationResponse(projectRepository.save(project));

    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    public AddMemberToProjectResponse addMemberToProject(AddMemberToProjectRequest request, UUID projectId) {


        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));

        validateProjectManager(project);

        if(request.getUsernames().isEmpty()){
            throw new ApplicationException(ErrorCode.NO_USERNAME);
        }

        //add all members to the project
        Set<User> members = userRepository.findAllByUsernameIn(request.getUsernames());
        if(members.size() != request.getUsernames().size()){
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        members.forEach((member) -> {
            project.getMembers().add(member);
            member.getProjects().add(project);
        });


        return projectMapper.toAddMemberToProjectResponse(projectRepository.save(project));
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public UserResponse getProjectManager(UUID projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
        return userMapper.toUserResponse(project.getManager());

    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Page<UserResponse> getProjectMembers(UUID projectId, Pageable pageable) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
        Page<User> members = userRepository.findAllByProjects_Id(projectId, pageable);
        return members.map(userMapper::toUserResponse);

    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Page<ProjectResponse> getProjects(Pageable pageable) {

        Page<Project> projects = projectRepository.findAll(pageable);
        return projects.map(projectMapper::toProjectResponse);

    }

    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    @Transactional
    @Override
    public void deleteProject(UUID projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        validateProjectManager(project);
        manager.getManagedProjects().remove(project);
        projectRepository.delete(project);
        if(manager.getManagedProjects().isEmpty() && manager.getRole() != Role.ADMIN){
            manager.setRole(Role.USER);
        }

    }

    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    @Transactional
    @Override
    public DeleteMemberFromProjectResponse deleteMemberFromProject(DeleteMemberFromProjectRequest request, UUID projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));

        validateProjectManager(project);

        if(request.getUsernames().isEmpty()){
            throw new ApplicationException(ErrorCode.NO_USERNAME);
        }

        Set<User> members = userRepository.findAllByUsernameIn(request.getUsernames());
        if(members.size() != request.getUsernames().size()){
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        members.forEach((member) -> {
            project.getMembers().remove(member);
            member.getProjects().remove(project);
        });

        return projectMapper.toDeleteMemberFromProjectResponse(projectRepository.save(project));

    }

    private void validateProjectManager(Project project){

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
        if(manager.getRole() != Role.ADMIN && !Objects.equals(project.getManager().getUsername(), authentication.getName())){
            throw new ApplicationException(ErrorCode.PROJECT_ACCESS_DENIED);
        }

    }

}
