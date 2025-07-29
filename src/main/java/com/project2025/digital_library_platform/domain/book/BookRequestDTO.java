package com.project2025.digital_library_platform.domain.book;

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

    @NotBlank(message = "Autor é necessário.")
    private String author;

    @NotBlank(message = "ISBN é necessário.")
    private String isbn;

    @NotNull(message = "Ano de publicação é necessário.")
    private Integer publicationYear;

    @NotBlank(message = "Editora é necessária.")
    private String publisherName;

    @NotNull(message = "Status é necessário.")
    private Status status;
}
