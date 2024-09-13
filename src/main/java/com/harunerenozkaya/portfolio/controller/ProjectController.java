package com.harunerenozkaya.portfolio.controller;

import com.harunerenozkaya.portfolio.controller.request.CreateProjectRequestModel;
import com.harunerenozkaya.portfolio.controller.request.UpdateProjectRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.harunerenozkaya.portfolio.dto.ProjectDto;
import com.harunerenozkaya.portfolio.service.ProjectService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/projects/")
@Tag(name = "Project Management System", description = "Operations pertaining to projects in the Portfolio application")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "View a list of available projects", description = "Get all projects stored in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @io.swagger.v3.oas.annotations.media.Content),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @io.swagger.v3.oas.annotations.media.Content),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @GetMapping("")
    public ResponseEntity<List<ProjectDto>> getProjects() {
        logger.info("Fetching all projects");
        List<ProjectDto> projects = projectService.getAllProjects();
        logger.info("Retrieved {} projects", projects.size());
        return ResponseEntity.ok(projects);
    }

    @Operation(summary = "Get a project by Id", description = "Get a single project by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved project"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @io.swagger.v3.oas.annotations.media.Content),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @io.swagger.v3.oas.annotations.media.Content),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(
            @Parameter(description = "Project id from which project object will retrieve", required = true)
            @PathVariable Long id) {
        logger.info("Fetching project with id: {}", id);
        ProjectDto project = projectService.getProjectById(id);
        logger.info("Retrieved project: {}", project);
        return ResponseEntity.ok(project);
    }

    @Operation(summary = "Add a new project", description = "Create a new project and store in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created project"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @io.swagger.v3.oas.annotations.media.Content),
    })
    @PostMapping("")
    public ResponseEntity<ProjectDto> createProject(
            @Parameter(description = "Project object store in database table", required = true)
            @RequestBody CreateProjectRequestModel project) {
        logger.info("Creating new project: {}", project);
        ProjectDto createdProject = projectService.createProject(project);
        logger.info("Created new project with id: {}", createdProject.getId());
        return ResponseEntity.ok(createdProject);
    }

    @Operation(summary = "Update a project", description = "Update an existing project in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated project"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @io.swagger.v3.oas.annotations.media.Content),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @Parameter(description = "Project Id to update project object", required = true)
            @PathVariable Long id,
            @Parameter(description = "Update project object", required = true)
            @RequestBody UpdateProjectRequestModel project) {
        logger.info("Updating project with id: {}", id);
        ProjectDto updatedProject = projectService.updateProject(id, project);
        logger.info("Updated project: {}", updatedProject);
        return ResponseEntity.ok(updatedProject);
    }

    @Operation(summary = "Delete a project", description = "Delete a project from the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted project"),
        @ApiResponse(responseCode = "404", description = "Project not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "Project Id from which project object will delete from database table", required = true)
            @PathVariable Long id) {
        logger.info("Deleting project with id: {}", id);
        projectService.deleteProject(id);
        logger.info("Deleted project with id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
