package com.project2025.digital_library_platform.domain.loginDTO;

import com.project2025.digital_library_platform.domain.user.Role;

public record LoginResponseDTO(String token, Role role) {
}
