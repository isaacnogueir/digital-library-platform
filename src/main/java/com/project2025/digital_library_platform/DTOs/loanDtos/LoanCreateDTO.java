package com.project2025.digital_library_platform.DTOs.loanDtos;

import jakarta.validation.constraints.NotNull;

public record LoanCreateDTO(
        @NotNull Long userId,
        @NotNull Long bookId) {
}
