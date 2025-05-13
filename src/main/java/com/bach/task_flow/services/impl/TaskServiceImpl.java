package com.bach.task_flow.services.impl;

import com.bach.task_flow.domains.Project;
import com.bach.task_flow.domains.Task;
import com.bach.task_flow.domains.User;
import com.bach.task_flow.dtos.requests.task.AddUserToTaskRequest;
import com.bach.task_flow.dtos.requests.task.StatusUpdateRequest;
import com.bach.task_flow.dtos.requests.task.TaskCreationRequest;
import com.bach.task_flow.dtos.requests.task.TaskUpdateRequest;
import com.bach.task_flow.dtos.responses.task.AddUserToTaskResponse;
import com.bach.task_flow.dtos.responses.task.StatusUpdateResponse;
import com.bach.task_flow.dtos.responses.task.TaskCreationResponse;
import com.bach.task_flow.dtos.responses.task.TaskUpdateResponse;
import com.bach.task_flow.enums.Role;
import com.bach.task_flow.enums.Status;
import com.bach.task_flow.exceptions.ApplicationException;
import com.bach.task_flow.exceptions.ErrorCode;
import com.bach.task_flow.mappers.TaskMapper;
import com.bach.task_flow.repositories.ProjectRepository;
import com.bach.task_flow.repositories.TaskRepository;
import com.bach.task_flow.repositories.UserRepository;
import com.bach.task_flow.services.ProjectService;
import com.bach.task_flow.services.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    ProjectRepository projectRepository;
    UserRepository userRepository;
    TaskMapper taskMapper;


    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    @Override
    public TaskCreationResponse createTask(TaskCreationRequest request) {

        Project project = projectRepository.findById(request.getProject_id())
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
        validateProjectManager(project);
        if(request.getDueDate() == null || request.getDueDate().isBefore(LocalDate.now())){
            throw new ApplicationException(ErrorCode.INVALID_DUE_DATE);
        }
        Task task = taskMapper.toTask(request);
        task.setProject(project);
        project.getTasks().add(task);
        if(request.getStatus() == null){
            task.setStatus(Status.TODO);
        }
        return taskMapper.toTaskCreationResponse(taskRepository.save(task));

    }

    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    @Override
    public TaskUpdateResponse updateTask(UUID taskId, TaskUpdateRequest request) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.TASK_NOT_FOUND));
        if(authentication.getName().equals(task.getProject().getManager().getUsername())){
            throw new ApplicationException(ErrorCode.TASK_ACCESS_DENIED);
        }
        if(request.getDueDate() == null || request.getDueDate().isBefore(LocalDate.now())){
            throw new ApplicationException(ErrorCode.INVALID_DUE_DATE);
        }
        taskMapper.updateTask(task, request);
        return taskMapper.toTaskUpdateResponse(taskRepository.save(task));

    }

    @Override
    public void deleteTask(UUID taskId, UUID projectId) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PROJECT_NOT_FOUND));
        if(authentication.getName().equals(project.getManager().getUsername())){
            taskRepository.deleteById(taskId);
        }

    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public StatusUpdateResponse updateStatus(UUID taskId, StatusUpdateRequest request) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.TASK_NOT_FOUND));
        Project project = task.getProject();
        if(!project.getManager().getUsername().equals(authentication.getName())
                && !taskRepository.existsByIdAndUsers_Username(taskId, authentication.getName())){
            throw new ApplicationException(ErrorCode.TASK_ACCESS_DENIED);
        }
        LocalDate dueDate = task.getDueDate();
        if(request.getStatus() == Status.DONE
                && dueDate.isBefore(LocalDate.now())){
            task.setStatus(Status.LATE);
        }else{
            task.setStatus(request.getStatus());
        }
        return taskMapper.toStatusUpdateResponse(taskRepository.save(task));

    }

    @PreAuthorize("hasAuthority('SCOPE_MANAGER')")
    @Override
    public AddUserToTaskResponse addUsersToTask(UUID taskId, AddUserToTaskRequest request) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ApplicationException(ErrorCode.TASK_NOT_FOUND));
        if(!task.getProject().getManager().getUsername().equals(authentication.getName())){
            throw new ApplicationException(ErrorCode.TASK_ACCESS_DENIED);
        }
        if(request.getUsernames().isEmpty()){
            throw new ApplicationException(ErrorCode.NO_USERNAME);
        }

        Set<User> members = userRepository.findAllByUsernameIn(request.getUsernames());
        if(members.size() != request.getUsernames().size()){
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        members.forEach((member) -> {
            if(!task.getProject().getMembers().contains(member)){
                throw new ApplicationException(ErrorCode.TASK_ACCESS_DENIED);
            }
            task.getUsers().add(member);
            member.getTasks().add(task);
        });

        return taskMapper.toAddUserToTaskResponse(taskRepository.save(task));

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
