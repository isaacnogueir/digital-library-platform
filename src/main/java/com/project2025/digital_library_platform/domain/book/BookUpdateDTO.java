package com.project2025.digital_library_platform.domain.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookUpdateDTO(
        @NotBlank String title,
        @NotBlank String author,
        @NotBlank String isbn,
        @NotNull Integer publicationYear,
        @NotBlank String publisherName
) {
}

