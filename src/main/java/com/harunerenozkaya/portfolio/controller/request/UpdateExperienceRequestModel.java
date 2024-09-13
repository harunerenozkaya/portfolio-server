package com.harunerenozkaya.portfolio.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateExperienceRequestModel {
    private String companyName;
    private String companyLogo;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String detail;
    private List<String> usedSkills;
}
