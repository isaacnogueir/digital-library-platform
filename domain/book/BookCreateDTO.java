package com.project2025.auth_2.domain.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateDTO(@NotBlank String title,
                            @NotBlank String author,
                            @NotBlank String isbn,
                            @NotBlank Integer publicationYear,
                            @NotBlank String publisherName,
                            @NotNull Status status) {
}
