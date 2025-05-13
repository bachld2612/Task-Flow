package com.bach.task_flow.services;

import com.bach.task_flow.dtos.requests.task.AddUserToTaskRequest;
import com.bach.task_flow.dtos.requests.task.StatusUpdateRequest;
import com.bach.task_flow.dtos.requests.task.TaskCreationRequest;
import com.bach.task_flow.dtos.requests.task.TaskUpdateRequest;
import com.bach.task_flow.dtos.responses.task.AddUserToTaskResponse;
import com.bach.task_flow.dtos.responses.task.StatusUpdateResponse;
import com.bach.task_flow.dtos.responses.task.TaskCreationResponse;
import com.bach.task_flow.dtos.responses.task.TaskUpdateResponse;

import java.util.UUID;

public interface TaskService {

    TaskCreationResponse createTask(TaskCreationRequest request);
    TaskUpdateResponse updateTask(UUID taskId, TaskUpdateRequest request);
    void deleteTask(UUID taskId, UUID projectId);
    StatusUpdateResponse updateStatus(UUID taskId, StatusUpdateRequest request);
    AddUserToTaskResponse addUsersToTask(UUID taskId, AddUserToTaskRequest request);

}
