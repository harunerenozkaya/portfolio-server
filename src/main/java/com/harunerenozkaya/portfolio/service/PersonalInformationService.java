package com.harunerenozkaya.portfolio.service;

import com.harunerenozkaya.portfolio.dto.PersonalInformationDto;
import com.harunerenozkaya.portfolio.model.PersonalInformation;
import com.harunerenozkaya.portfolio.repository.PersonalInformationRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

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

        // Clear current personal information
        personalInformationRepository.deleteAll();

        // Save updated personal information
        PersonalInformation personalInformation = modelMapper.map(personalInformationDto, PersonalInformation.class);
        PersonalInformation savedPersonalInformation = personalInformationRepository.save(personalInformation);
        PersonalInformationDto savedDto = modelMapper.map(savedPersonalInformation, PersonalInformationDto.class);

        logger.info("Updated personal information");
        return savedDto;
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
