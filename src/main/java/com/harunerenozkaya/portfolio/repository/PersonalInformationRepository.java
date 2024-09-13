package com.harunerenozkaya.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harunerenozkaya.portfolio.model.PersonalInformation;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {

}
