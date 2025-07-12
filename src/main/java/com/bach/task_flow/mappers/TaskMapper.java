package com.bach.task_flow.mappers;

import com.bach.task_flow.domains.Project;
import com.bach.task_flow.domains.Task;
import com.bach.task_flow.domains.User;
import com.bach.task_flow.dtos.requests.task.TaskCreationRequest;
import com.bach.task_flow.dtos.requests.task.TaskUpdateRequest;
import com.bach.task_flow.dtos.responses.task.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.lang.annotation.Target;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface TaskMapper {

    Task toTask(TaskCreationRequest task);
    TaskCreationResponse toTaskCreationResponse(Task task);
    TaskUpdateResponse toTaskUpdateResponse(Task task);
    void updateTask(@MappingTarget Task task, TaskUpdateRequest taskUpdateRequest);
    TaskResponse toTaskResponse(Task task);
    StatusUpdateResponse toStatusUpdateResponse(Task task);
    @Mapping(target = "usernames", source = "users")
    AddUserToTaskResponse toAddUserToTaskResponse(Task task);
    @Mapping(target = "usernames", source = "users")
    DeleteUserFromTaskResponse toDeleteUserFromTaskResponse(Task task);

    default String map(User user) {
        return user.getUsername();
    }
}
