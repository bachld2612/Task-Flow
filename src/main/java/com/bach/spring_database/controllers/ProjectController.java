package com.bach.spring_database.controllers;

import com.bach.spring_database.dtos.ApiResponse;
import com.bach.spring_database.dtos.requests.project.AddMemberToProjectRequest;
import com.bach.spring_database.dtos.requests.project.DeleteMemberFromProjectRequest;
import com.bach.spring_database.dtos.requests.project.ProjectCreationRequest;
import com.bach.spring_database.dtos.responses.project.AddMemberToProjectResponse;
import com.bach.spring_database.dtos.responses.project.DeleteMemberFromProjectResponse;
import com.bach.spring_database.dtos.responses.project.ProjectCreationResponse;
import com.bach.spring_database.dtos.responses.project.ProjectResponse;
import com.bach.spring_database.dtos.responses.user.UserResponse;
import com.bach.spring_database.services.impl.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
                               "code": 1000,
                               "result": {
                                 "name": "pet project 2",
                                 "description": "pet project of bach company",
                                 "managerName": "bachld"
                               }
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

    @Operation(
            summary     = "Get Project Manager",
            description = "Retrieves the manager of the specified project"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Manager retrieved successfully",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1000,
                              "result": {
                                "id": "ef34941a-5d42-4457-8783-d9643c6950b5",
                                "username": "bachld",
                                "email": "lyduybach800@gmail.com",
                                "avatarUrl": "https://res.cloudinary.com/dpvxx0v6y/image/upload/v1746025014/xhayiexs0ifbzgvqgenj.jpg",
                                "role": "MANAGER",
                                "enabled": true,
                                "updatedAt": "2025-05-02"
                              }
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
    @GetMapping("/{projectId}/manager")
    public ApiResponse<UserResponse> getProjectManager(@PathVariable UUID projectId) {

        return ApiResponse.<UserResponse>builder()
                .result(projectService.getProjectManager(projectId))
                .build();

    }


    @Operation(
            summary     = "Get Project Members",
            description = "Retrieves a paginated list of members for the specified project"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Members retrieved successfully",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1000,
                              "result": {
                                "content": [
                                  {
                                    "id": "af2f7314-44c0-4c9a-b15f-62141472f867",
                                    "username": "bachld1",
                                    "email": "lyduybach700@gmail.com",
                                    "avatarUrl": "/images/default_avatar.png",
                                    "role": "USER",
                                    "enabled": true,
                                    "updatedAt": "2025-05-02"
                                  }
                                ],
                                "pageable": {
                                  "pageNumber": 0,
                                  "pageSize": 10,
                                  "sort": {
                                    "empty": false,
                                    "sorted": true,
                                    "unsorted": false
                                  },
                                  "offset": 0,
                                  "paged": true,
                                  "unpaged": false
                                },
                                "last": true,
                                "totalElements": 1,
                                "totalPages": 1,
                                "size": 10,
                                "number": 0,
                                "sort": {
                                  "empty": false,
                                  "sorted": true,
                                  "unsorted": false
                                },
                                "first": true,
                                "numberOfElements": 1,
                                "empty": false
                              }
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
    @GetMapping("/{projectId}/members")
    public ApiResponse<Page<UserResponse>> getProjectMembers(
            @PathVariable UUID projectId,
            @PageableDefault(page = 0, size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable

    ) {

        return ApiResponse.<Page<UserResponse>>builder()
                .result(projectService.getProjectMembers(projectId, pageable))
                .build();

    }

    @Operation(
            summary     = "Get All Projects",
            description = "Retrieves a paginated list of all projects"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Projects retrieved successfully",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1000,
                              "result": {
                                "content": [
                                  {
                                    "id": "bdb24f1b-fd83-420a-80f2-209c1a917fb0",
                                    "name": "Bach project",
                                    "description": "This project for Bach company, you can do anything here if you are in the company",
                                    "updatedAt": "2025-05-02"
                                  }
                                ],
                                "pageable": {
                                  "pageNumber": 0,
                                  "pageSize": 10,
                                  "sort": {
                                    "empty": false,
                                    "sorted": true,
                                    "unsorted": false
                                  },
                                  "offset": 0,
                                  "paged": true,
                                  "unpaged": false
                                },
                                "last": true,
                                "totalPages": 1,
                                "totalElements": 1,
                                "first": true,
                                "numberOfElements": 1,
                                "size": 10,
                                "number": 0,
                                "sort": {
                                  "empty": false,
                                  "sorted": true,
                                  "unsorted": false
                                },
                                "empty": false
                              }
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
    @GetMapping
    public ApiResponse<Page<ProjectResponse>> getProjects(
            @PageableDefault(page = 0, size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ){

        return ApiResponse.<Page<ProjectResponse>>builder()
                .result(projectService.getProjects(pageable))
                .build();

    }

    @Operation(
            summary     = "Delete Project",
            description = "Deletes the specified project. Access restricted to users with ADMIN role or the project’s manager."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description  = "Project deleted successfully",
                    content      = @Content(
                            mediaType = "application/json",
                            schema    = @Schema(example = """
                            {
                              "code": 1000,
                              "result": "Deleted project successfully"
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
    @DeleteMapping("/{projectId}")
    public ApiResponse<String> deleteProject(@PathVariable UUID projectId) {

        projectService.deleteProject(projectId);
        return ApiResponse.<String>builder()
                .message("Deleted project successfully")
                .build();

    }

    @PutMapping("/{projectId}/delete-member")
    public ApiResponse<DeleteMemberFromProjectResponse> deleteMemberFromProject(@PathVariable UUID projectId, @RequestBody DeleteMemberFromProjectRequest request) {

        return ApiResponse.<DeleteMemberFromProjectResponse>builder()
                .result(projectService.deleteMemberFromProject(request, projectId))
                .build();

    }

}
