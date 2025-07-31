package com.project2025.digital_library_platform.domain.book.googleBooks;

public record GoogleBookSelectionDTO (
            String googleBooksId,
            String title,
            String authors,
            String publisher,
            String publishedDate,
            String description,
            String thumbnailUrl,
            String isbn10,
            String isbn13,
            Integer pageCount
    ) {}
