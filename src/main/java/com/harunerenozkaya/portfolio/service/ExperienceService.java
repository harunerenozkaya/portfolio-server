package com.harunerenozkaya.portfolio.service;

import com.harunerenozkaya.portfolio.controller.request.CreateExperienceRequestModel;
import com.harunerenozkaya.portfolio.controller.request.UpdateExperienceRequestModel;
import com.harunerenozkaya.portfolio.dto.ExperienceDto;
import com.harunerenozkaya.portfolio.model.Experience;
import com.harunerenozkaya.portfolio.repository.ExperienceRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ExperienceService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExperienceService.class);
    
    private final ExperienceRepository experienceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ExperienceService(ExperienceRepository experienceRepository, ModelMapper modelMapper) {
        this.experienceRepository = experienceRepository;
        this.modelMapper = modelMapper;
        logger.info("ExperienceService initialized");
    }

    public List<ExperienceDto> getAllExperiences() {
        logger.info("Fetching all experiences");
        List<Experience> experiences = experienceRepository.findAll();
        List<ExperienceDto> experienceDtos = experiences.stream()
            .map(experience -> modelMapper.map(experience, ExperienceDto.class))
            .collect(Collectors.toList());
        logger.info("Retrieved {} experiences", experienceDtos.size());
        return experienceDtos;
    }

    public ExperienceDto getExperienceById(Long id) {
        logger.info("Fetching experience with id: {}", id);
        Experience experience = experienceRepository.findById(id).orElse(null);
        if (experience == null) {
            logger.error("Experience not found with id: {}", id);
            throw new RuntimeException("Experience not found");
        }
        ExperienceDto experienceDto = modelMapper.map(experience, ExperienceDto.class);
        logger.info("Retrieved experience: {}", experienceDto);
        return experienceDto;
    }

    public ExperienceDto createExperience(CreateExperienceRequestModel experience) {
        logger.info("Creating new experience: {}", experience);
        Experience experienceEntity = modelMapper.map(experience, Experience.class);
        Experience savedExperience = experienceRepository.save(experienceEntity);
        ExperienceDto savedExperienceDto = modelMapper.map(savedExperience, ExperienceDto.class);
        logger.info("Created new experience with id: {}", savedExperienceDto.getId());
        return savedExperienceDto;
    }

    public ExperienceDto updateExperience(Long id, UpdateExperienceRequestModel updatedExperienceModel) {
        logger.info("Updating experience with id: {}", id);

        Experience experience = experienceRepository.findById(id).orElseThrow(() -> {
            logger.error("Experience not found with id: {}", id);
            return new RuntimeException("Experience not found");
        });

        modelMapper.map(updatedExperienceModel, experience);
        experienceRepository.save(experience);

        ExperienceDto updatedExperienceDto = modelMapper.map(experience, ExperienceDto.class);
        logger.info("Updated experience: {}", updatedExperienceDto);
        return updatedExperienceDto;
    }

    public void deleteExperience(Long id) {
        logger.info("Deleting experience with id: {}", id);
        Experience experience = experienceRepository.findById(id).orElse(null);
        if (experience == null) {
            logger.error("Experience not found with id: {}", id);
            throw new RuntimeException("Experience not found");
        }
        experienceRepository.delete(experience);
        logger.info("Deleted experience with id: {}", id);
    }
}
