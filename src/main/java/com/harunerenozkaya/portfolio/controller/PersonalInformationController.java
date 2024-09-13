package com.harunerenozkaya.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.harunerenozkaya.portfolio.dto.PersonalInformationDto;
import com.harunerenozkaya.portfolio.service.PersonalInformationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/personal-information/")
@Tag(name = "Personal Information Management System", description = "Operations pertaining to personal information in the Portfolio application")
public class PersonalInformationController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalInformationController.class);

    @Autowired
    private PersonalInformationService personalInformationService;

    @Operation(summary = "View personal information", description = "Retrieves personal information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved personal information"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @GetMapping("")
    public ResponseEntity<PersonalInformationDto> getPersonalInformation() {
        logger.info("Fetching personal information");
        PersonalInformationDto personalInfo = personalInformationService.getPersonalInformation();
        logger.info("Retrieved personal information");
        return ResponseEntity.ok(personalInfo);
    }

    @Operation(summary = "Update personal information", description = "Updates existing personal information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated personal information"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @io.swagger.v3.oas.annotations.media.Content),
    })
    @PutMapping("")
    public ResponseEntity<PersonalInformationDto> updatePersonalInformation(
            @Parameter(description = "Personal information object to update", required = true)
            @RequestBody PersonalInformationDto personalInformation) {
        logger.info("Updating personal information");
        PersonalInformationDto updatedInfo = personalInformationService.updatePersonalInformation(personalInformation);
        logger.info("Updated personal information");
        return ResponseEntity.ok(updatedInfo);
    }

    @Operation(summary = "Create new personal information", description = "Creates new personal information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created personal information"),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @io.swagger.v3.oas.annotations.media.Content),
    })
    @PostMapping("")
    public ResponseEntity<PersonalInformationDto> createPersonalInformation(
            @Parameter(description = "Personal information object to create", required = true)
            @RequestBody PersonalInformationDto personalInformation) {
        logger.info("Creating new personal information");
        PersonalInformationDto createdInfo = personalInformationService.createPersonalInformation(personalInformation);
        logger.info("Created new personal information");
        return ResponseEntity.ok(createdInfo);
    }
}
