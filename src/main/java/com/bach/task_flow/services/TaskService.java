package com.bach.task_flow.services;

import com.bach.task_flow.dtos.requests.task.*;
import com.bach.task_flow.dtos.responses.task.*;
import com.bach.task_flow.dtos.responses.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TaskService {

    TaskCreationResponse createTask(TaskCreationRequest request);
    TaskUpdateResponse updateTask(UUID taskId, TaskUpdateRequest request);
    void deleteTask(UUID taskId, UUID projectId);
    StatusUpdateResponse updateStatus(UUID taskId, StatusUpdateRequest request);
    AddUserToTaskResponse addUsersToTask(UUID taskId, AddUserToTaskRequest request);
    DeleteUserFromTaskResponse deleteUserFromTask(UUID taskId, DeleteUserFromTaskRequest request);
    Page<TaskResponse> getAllTasks(Pageable pageable);
    TaskResponse getTask(UUID taskId);
    Page<UserResponse> getUsers(UUID taskId, Pageable pageable);

}
