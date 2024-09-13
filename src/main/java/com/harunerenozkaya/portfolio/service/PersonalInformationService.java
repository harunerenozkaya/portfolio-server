package com.harunerenozkaya.portfolio.service;

import com.harunerenozkaya.portfolio.dto.PersonalInformationDto;
import com.harunerenozkaya.portfolio.model.PersonalInformation;
import com.harunerenozkaya.portfolio.repository.PersonalInformationRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                return new RuntimeException("Personal Information not found");
            });
        PersonalInformationDto dto = modelMapper.map(personalInformation, PersonalInformationDto.class);
        logger.info("Retrieved personal information");
        return dto;
    }

    public PersonalInformationDto updatePersonalInformation(PersonalInformationDto personalInformationDto) {
        logger.info("Updating personal information");
        PersonalInformation existingPersonalInformation = personalInformationRepository.findAll().stream().findFirst()
            .orElseThrow(() -> {
                logger.error("Personal Information not found for update");
                return new RuntimeException("Personal Information not found");
            });
        modelMapper.map(personalInformationDto, existingPersonalInformation);
        PersonalInformation updatedPersonalInformation = personalInformationRepository.save(existingPersonalInformation);
        PersonalInformationDto updatedDto = modelMapper.map(updatedPersonalInformation, PersonalInformationDto.class);
        logger.info("Personal information updated successfully");
        return updatedDto;
    }

    public PersonalInformationDto createPersonalInformation(PersonalInformationDto personalInformationDto) {
        logger.info("Creating new personal information");
        if (!personalInformationRepository.findAll().isEmpty()) {
            logger.error("Attempt to create personal information when it already exists");
            throw new RuntimeException("Personal Information already exists");
        }
        PersonalInformation personalInformation = modelMapper.map(personalInformationDto, PersonalInformation.class);
        PersonalInformation savedPersonalInformation = personalInformationRepository.save(personalInformation);
        PersonalInformationDto savedDto = modelMapper.map(savedPersonalInformation, PersonalInformationDto.class);
        logger.info("New personal information created successfully");
        return savedDto;
    }
}
