package com.harunerenozkaya.portfolio.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjectRequestModel {
    private String name;
    private String detail;
    private List<String> skills;
    private String logoUrl;
    private String url;
}
