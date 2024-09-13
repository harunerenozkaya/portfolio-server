package com.harunerenozkaya.portfolio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInformationDto {
    private String name;
    private String surname;
    private String job;
    private String summary;
    private String biography;
    private List<String> skills;
    private List<SocialMediaLinkDto> socialMediaLinks;
    private String personalImageUrl;
}
