package com.project2025.auth_2.controllers;

import com.project2025.auth_2.domain.loginDTO.LoginDTO;
import com.project2025.auth_2.domain.loginDTO.LoginResponseDTO;
import com.project2025.auth_2.domain.loginDTO.RegisterDTO;
import com.project2025.auth_2.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Long register(@Valid @RequestBody RegisterDTO dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginDTO dto) {
        return authService.login(dto);
    }



}