package com.harunerenozkaya.portfolio.service;

import com.harunerenozkaya.portfolio.dto.PersonalInformationDto;
import com.harunerenozkaya.portfolio.model.PersonalInformation;
import com.harunerenozkaya.portfolio.model.SocialMediaLink;
import com.harunerenozkaya.portfolio.repository.PersonalInformationRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PersonalInformationService {
    
    private static final Logger logger = LoggerFactory.getLogger(PersonalInformationService.class);
    
    private final PersonalInformationRepository personalInformationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonalInformationService(PersonalInformationRepository personalInformationRepository, ModelMapper modelMapper) {
        this.personalInformationRepository = personalInformationRepository;
        this.modelMapper = modelMapper;
        logger.info("PersonalInformationService initialized");
    }

    public PersonalInformationDto getPersonalInformation() {
        logger.info("Fetching personal information");
        PersonalInformation personalInformation = personalInformationRepository.findAll().stream().findFirst()
            .orElseThrow(() -> {
                logger.error("Personal Information not found");
                return new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal Information not found");
            });
        PersonalInformationDto dto = modelMapper.map(personalInformation, PersonalInformationDto.class);
        logger.info("Retrieved personal information");
        return dto;
    }

    public PersonalInformationDto updatePersonalInformation(PersonalInformationDto personalInformationDto) {
        logger.info("Updating personal information");

        // Retrieve existing personal information or throw a 404 exception
        PersonalInformation existingPersonalInformation = personalInformationRepository.findAll().stream().findFirst()
                .orElseThrow(() -> {
                    logger.error("Personal Information not found for update");
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Personal Information not found for update");
                });

        // Handle the socialMediaLinks collection to avoid orphan removal issues
        if (personalInformationDto.getSocialMediaLinks() != null) {
            // Clear the current list and replace with the updated social media links from DTO
            existingPersonalInformation.getSocialMediaLinks().clear();
            existingPersonalInformation.getSocialMediaLinks().addAll(
                    personalInformationDto.getSocialMediaLinks().stream()
                            .map(linkDto -> modelMapper.map(linkDto, SocialMediaLink.class))
                            .collect(Collectors.toList())
            );
        }

        // Map the rest of the properties from the DTO to the existing entity
        modelMapper.map(personalInformationDto, existingPersonalInformation);

        // Save the updated personal information entity
        PersonalInformation updatedPersonalInformation = personalInformationRepository.save(existingPersonalInformation);

        // Map the updated entity back to DTO and return
        PersonalInformationDto updatedDto = modelMapper.map(updatedPersonalInformation, PersonalInformationDto.class);

        logger.info("Personal information updated successfully");
        return updatedDto;
    }

    public PersonalInformationDto createPersonalInformation(PersonalInformationDto personalInformationDto) {
        logger.info("Creating new personal information");
        if (!personalInformationRepository.findAll().isEmpty()) {
            logger.error("Attempt to create personal information when it already exists");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Personal Information already exists");
        }
        PersonalInformation personalInformation = modelMapper.map(personalInformationDto, PersonalInformation.class);
        PersonalInformation savedPersonalInformation = personalInformationRepository.save(personalInformation);
        PersonalInformationDto savedDto = modelMapper.map(savedPersonalInformation, PersonalInformationDto.class);
        logger.info("New personal information created successfully");
        return savedDto;
    }
}
