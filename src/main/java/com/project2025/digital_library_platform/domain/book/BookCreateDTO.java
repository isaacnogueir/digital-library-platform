package com.project2025.digital_library_platform.domain.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateDTO(@NotBlank String title,
                            @NotBlank String author,
                            @NotBlank String isbn,
                            @NotBlank Integer publicationYear,
                            @NotBlank String publisherName,
                            @NotNull Status status) {
}
