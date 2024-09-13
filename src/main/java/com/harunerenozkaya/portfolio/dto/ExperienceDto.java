package com.harunerenozkaya.portfolio.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {
    private Long id;
    private String companyName;
    private String companyLogo;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String detail;
    private List<String> usedSkills;
}
