package com.harunerenozkaya.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@Tag(name = "Login System")
public class LoginController {
    @Operation(summary = "Login user", description = "Empty login endpoint which is protected by Spring Security")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in"),
    })
    @GetMapping("")
    public ResponseEntity login() {
        return ResponseEntity.ok("Login successful");
    }
}
