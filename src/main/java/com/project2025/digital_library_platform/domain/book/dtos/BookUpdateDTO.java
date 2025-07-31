package com.project2025.digital_library_platform.domain.book.dtos;

import jakarta.validation.constraints.NotBlank;

public record BookUpdateDTO(
        @NotBlank(message = "Título é obrigatório")
        String title,
        String authors,
        String publisher,
        String publishedDate,
        String isbn10,
        String isbn13,
        String description,
        String thumbnailUrl,
        Integer pageCount
) {}