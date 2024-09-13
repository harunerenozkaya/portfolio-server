package com.harunerenozkaya.portfolio.controller;

import com.harunerenozkaya.portfolio.controller.request.CreateExperienceRequestModel;
import com.harunerenozkaya.portfolio.controller.request.UpdateExperienceRequestModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.harunerenozkaya.portfolio.dto.ExperienceDto;
import com.harunerenozkaya.portfolio.service.ExperienceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/experience")
@Tag(name = "Experience Management System", description = "Operations pertaining to experiences in the Portfolio application")
public class ExperienceController {
    private static final Logger logger = LoggerFactory.getLogger(ExperienceController.class);

    @Autowired
    private ExperienceService experienceService;

    @Operation(summary = "View a list of available experiences", description = "Get all experiences stored in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @io.swagger.v3.oas.annotations.media.Content),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @io.swagger.v3.oas.annotations.media.Content),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @GetMapping("")
    public ResponseEntity<List<ExperienceDto>> getExperience() {
        logger.info("Fetching all experiences");
        List<ExperienceDto> experiences = experienceService.getAllExperiences();
        logger.info("Retrieved {} experiences", experiences.size());
        return ResponseEntity.ok(experiences);
    }

    @Operation(summary = "Add a new experience", description = "Create a new experience and store in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created experience"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @io.swagger.v3.oas.annotations.media.Content),
    })
    @PostMapping("")
    public ResponseEntity<ExperienceDto> createExperience(
            @Parameter(description = "Experience object to be stored in database", required = true)
            @RequestBody CreateExperienceRequestModel experience) {
        logger.info("Creating new experience: {}", experience);
        ExperienceDto createdExperience = experienceService.createExperience(experience);
        logger.info("Created new experience with ID: {}", createdExperience.getId());
        return ResponseEntity.ok(createdExperience);
    }

    @Operation(summary = "Update an existing experience", description = "Update an experience in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated experience"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @io.swagger.v3.oas.annotations.media.Content),
        @ApiResponse(responseCode = "404", description = "Experience not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ExperienceDto> updateExperience(
            @Parameter(description = "ID of the experience to be updated", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated experience object", required = true)
            @RequestBody UpdateExperienceRequestModel experience) {
        logger.info("Updating experience with ID: {}", id);
        ExperienceDto updatedExperience = experienceService.updateExperience(id, experience);
        logger.info("Updated experience with ID: {}", id);
        return ResponseEntity.ok(updatedExperience);
    }

    @Operation(summary = "Delete an experience", description = "Delete an experience from the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted experience"),
        @ApiResponse(responseCode = "404", description = "Experience not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExperience(
            @Parameter(description = "ID of the experience to be deleted", required = true)
            @PathVariable Long id) {
        logger.info("Deleting experience with ID: {}", id);
        experienceService.deleteExperience(id);
        logger.info("Deleted experience with ID: {}", id);
        return ResponseEntity.ok("Experience deleted");
    }
}
