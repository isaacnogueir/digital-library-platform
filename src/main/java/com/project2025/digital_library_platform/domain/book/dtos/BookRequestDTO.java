package com.project2025.digital_library_platform.domain.book.dtos;

import com.project2025.digital_library_platform.domain.book.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * DTO para requisição de livro.
 * Contém os dados necessários para criar um novo livro.
 */
@Setter
@AllArgsConstructor
public class BookRequestDTO {

    @NotBlank(message = "Título é necessário.")
    private String title;

    private String authors;

    private String isbn10;

    private String isbn13;

    private String publishedDate;

    private String publisher;

    private String description;

    private Integer pageCount;

    private String googleBooksId;

    @NotNull(message = "Status é necessário.")
    private Status status;
}