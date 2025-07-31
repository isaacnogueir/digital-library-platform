package com.project2025.digital_library_platform.domain.loan.Dtos;

import jakarta.validation.constraints.NotNull;

public record LoanCreateDTO(
        @NotNull Long userId,
        @NotNull Long bookId) {
}
