package com.bach.task_flow.controllers;


import com.bach.task_flow.dtos.ApiResponse;
import com.bach.task_flow.dtos.requests.task.AddUserToTaskRequest;
import com.bach.task_flow.dtos.requests.task.StatusUpdateRequest;
import com.bach.task_flow.dtos.requests.task.TaskCreationRequest;
import com.bach.task_flow.dtos.requests.task.TaskUpdateRequest;
import com.bach.task_flow.dtos.responses.task.AddUserToTaskResponse;
import com.bach.task_flow.dtos.responses.task.StatusUpdateResponse;
import com.bach.task_flow.dtos.responses.task.TaskCreationResponse;
import com.bach.task_flow.dtos.responses.task.TaskUpdateResponse;
import com.bach.task_flow.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Task APIs", description = "These APIs for task features")
public class TaskController {

    TaskService taskService;

    @Operation(
            summary     = "Create a New Task",
            description = "Creates a new task with the provided details. Only users with the MANAGER role may perform this operation."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Task created successfully",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1000,
                              "result": {
                                "title": "task 1",
                                "description": "this is task 1",
                                "status": "TODO",
                                "dueDate": "2025-05-13"
                              }
                            }
                      """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Invalid task data",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": "10xx",
                              "message": "Error Message"
                            }
                        """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description  = "Unauthorized",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1018,
                              "message": "Unauthorized"
                            }
                        """)
                    )
            )
    })
    @PostMapping
    public ApiResponse<TaskCreationResponse> createTask(@Valid TaskCreationRequest request) {

        return ApiResponse.<TaskCreationResponse>builder()
                .result(taskService.createTask(request))
                .build();

    }

    @PutMapping("/{taskId}")
    public ApiResponse<TaskUpdateResponse> updateTask(@Valid TaskUpdateRequest request, @PathVariable UUID taskId) {

        return ApiResponse.<TaskUpdateResponse>builder()
                .result(taskService.updateTask(taskId ,request))
                .build();

    }

    @DeleteMapping("/{taskId}")
    public ApiResponse<String> deleteTask(@PathVariable UUID taskId, UUID projectId) {

        taskService.deleteTask(taskId, projectId);
        return ApiResponse.<String>builder()
                .result("Deleted task successfully")
                .build();

    }

    @PatchMapping("/{taskId}/update-status")
    public ApiResponse<StatusUpdateResponse> updateTaskStatus(@PathVariable UUID taskId, StatusUpdateRequest request) {

        return ApiResponse.<StatusUpdateResponse>builder()
                .result(taskService.updateStatus(taskId, request))
                .build();

    }

    @PatchMapping("/{taskId}/add-users")
    public ApiResponse<AddUserToTaskResponse> addUserToTask(@PathVariable UUID taskId,
            @RequestBody AddUserToTaskRequest request){

        return ApiResponse.<AddUserToTaskResponse>builder()
                .result(taskService.addUsersToTask(taskId, request))
                .build();

    }

}
