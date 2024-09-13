package com.harunerenozkaya.portfolio.service;

import com.harunerenozkaya.portfolio.controller.request.CreateProjectRequestModel;
import com.harunerenozkaya.portfolio.controller.request.UpdateProjectRequestModel;
import com.harunerenozkaya.portfolio.dto.ProjectDto;
import com.harunerenozkaya.portfolio.model.Project;
import com.harunerenozkaya.portfolio.repository.ProjectRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProjectService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        logger.info("ProjectService initialized");
    }

    public List<ProjectDto> getAllProjects() {
        logger.info("Fetching all projects");
        List<Project> projects = projectRepository.findAll();
        List<ProjectDto> projectDtos = projects.stream()
            .map(project -> modelMapper.map(project, ProjectDto.class))
            .collect(Collectors.toList());
        logger.info("Retrieved {} projects", projectDtos.size());
        return projectDtos;
    }

    public ProjectDto getProjectById(Long id) {
        logger.info("Fetching project with id: {}", id);
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Project not found with id: {}", id);
                return new RuntimeException("Project not found");
            });
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        logger.info("Retrieved project: {}", projectDto);
        return projectDto;
    }

    public ProjectDto createProject(CreateProjectRequestModel projectRequestModel) {
        logger.info("Creating new project: {}", projectRequestModel);
        Project project = modelMapper.map(projectRequestModel, Project.class);
        Project savedProject = projectRepository.save(project);
        ProjectDto savedProjectDto = modelMapper.map(savedProject, ProjectDto.class);
        logger.info("Created new project with id: {}", savedProjectDto.getId());
        return savedProjectDto;
    }

    public ProjectDto updateProject(Long id, UpdateProjectRequestModel projectRequestModel) {
        logger.info("Updating project with id: {}", id);
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Project not found with id: {}", id);
                return new RuntimeException("Project not found");
            });
        modelMapper.map(projectRequestModel, project);
        Project updatedProject = projectRepository.save(project);
        ProjectDto updatedProjectDto = modelMapper.map(updatedProject, ProjectDto.class);
        logger.info("Updated project: {}", updatedProjectDto);
        return updatedProjectDto;
    }

    public void deleteProject(Long id) {
        logger.info("Deleting project with id: {}", id);
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Project not found with id: {}", id);
                return new RuntimeException("Project not found");
            });
        projectRepository.delete(project);
        logger.info("Deleted project with id: {}", id);
    }
}
