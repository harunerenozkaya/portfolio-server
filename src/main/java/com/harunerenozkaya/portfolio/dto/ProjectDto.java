package com.harunerenozkaya.portfolio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private String detail;
    private List<String> skills;
    private String logoUrl;
    private String url;
}
