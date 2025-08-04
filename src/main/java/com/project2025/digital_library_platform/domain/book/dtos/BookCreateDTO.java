package com.project2025.digital_library_platform.domain.book.dtos;

import com.project2025.digital_library_platform.domain.book.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateDTO(
        @NotBlank(message = "Título é obrigatório")
        String title,
        String authors,
        String publisher,
        String publishedDate,
        String isbn10,
        String isbn13,
        String description,
        Integer pageCount,
        String googleBooksId,
        @NotNull(message = "Status é obrigatório")
        Status status
) {}