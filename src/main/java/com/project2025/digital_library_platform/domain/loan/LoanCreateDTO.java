package com.project2025.digital_library_platform.domain.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LoanCreateDTO(
        @NotNull Long userId,
        @NotNull Long bookId) {
}
