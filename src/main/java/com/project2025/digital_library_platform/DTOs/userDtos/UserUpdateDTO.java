package com.project2025.digital_library_platform.DTOs.userDtos;

public record UserUpdateDTO(
        String nome,
        String login,
        String password,
        String email,
        String endereco,
        String telefone
){}
