package com.harunerenozkaya.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harunerenozkaya.portfolio.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
