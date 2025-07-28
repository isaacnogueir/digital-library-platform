package com.project2025.auth_2.domain.loginDTO;

import com.project2025.auth_2.domain.user.Role;

public record LoginResponseDTO(String token, Role role) {
}
