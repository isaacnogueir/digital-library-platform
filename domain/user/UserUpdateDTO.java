package com.project2025.auth_2.domain.user;

public record UserUpdateDTO(
        String nome,
        String login,
        String password,
        String email,
        String endereco,
        String telefone){}
