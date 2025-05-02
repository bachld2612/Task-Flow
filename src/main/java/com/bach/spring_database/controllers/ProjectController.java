package com.bach.spring_database.controllers;

import com.bach.spring_database.dtos.ApiResponse;
import com.bach.spring_database.dtos.requests.project.AddMemberToProjectRequest;
import com.bach.spring_database.dtos.requests.project.ProjectCreationRequest;
import com.bach.spring_database.dtos.responses.project.AddMemberToProjectResponse;
import com.bach.spring_database.dtos.responses.project.ProjectCreationResponse;
import com.bach.spring_database.services.impl.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Project API", description = "These APIs for project features and they are almost for manager , to use these APIs, please login as manager to get jwt token and provide it!")
public class ProjectController {

    ProjectService projectService;

    @Operation(
            summary     = "Create New Project",
            description = "Create a new project with the given name and description."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Project created successfully",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "name": "Project",
                              "description": "Project Description"
                            }
                        """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Invalid project data",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                                "code": "100x",
                                "message": "Error Message"
                            }
                        """)
                    )
            )
    })
    @PostMapping
    public ApiResponse<ProjectCreationResponse> createProject(@RequestBody ProjectCreationRequest projectCreationRequest) {

        return ApiResponse.<ProjectCreationResponse>builder()
                .result(projectService.createProject(projectCreationRequest))
                .build();

    }

    @Operation(
            summary     = "Add Members to Project",
            description = "Add one or more users to the specified project and return the project name along with the added member usernames."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Members added successfully",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1000,
                              "result": {
                                "project_name": "Bach project",
                                "memberNames": [
                                  "bachld1"
                                ]
                              }
                            }
                        """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description  = "Invalid request",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                                {
                                  "code": "100x",
                                  "message": "Error Message"
                                }
                            """)
                    )
            )
    })
    @PutMapping("/{projectId}/members")
    public ApiResponse<AddMemberToProjectResponse> addMemberToProject(@PathVariable UUID projectId, @RequestBody AddMemberToProjectRequest addMemberRequest) {

        return ApiResponse.<AddMemberToProjectResponse>builder()
                .result(projectService.addMemberToProject(addMemberRequest, projectId))
                .build();

    }

}
