package com.project2025.digital_library_platform.domain.user;

public record UserUpdateDTO(
        String nome,
        String login,
        String password,
        String email,
        String endereco,
        String telefone){}
