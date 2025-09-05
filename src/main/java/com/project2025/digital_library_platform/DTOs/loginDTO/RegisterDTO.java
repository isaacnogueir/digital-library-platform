package com.project2025.digital_library_platform.DTOs.loginDTO;

import com.project2025.digital_library_platform.entity.user.Role;
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
