package com.project2025.digital_library_platform.DTOs.googleBooksDtos;

public record GoogleBookSelectionDTO (
            String googleBooksId,
            String title,
            String authors,
            String publisher,
            String publishedDate,
            String description,
            String isbn10,
            String isbn13,
            Integer pageCount
    ) {}
