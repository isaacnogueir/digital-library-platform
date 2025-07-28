package com.project2025.auth_2.domain.loginDTO;

import com.project2025.auth_2.domain.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotBlank String login,
                          @NotBlank String nome,
                          @NotBlank String password,
                          @NotBlank String email,
                          @NotBlank String endereco,
                          @NotBlank String telefone,
                          @NotNull Role role) {
}
