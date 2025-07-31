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

    private String authors; // Mudou de author para authors

    private String isbn10; // Separado em dois campos

    private String isbn13;

    private String publishedDate; // Mudou de publicationYear para publishedDate

    private String publisher; // Mudou de publisherName para publisher

    private String description; // Novo campo

    private String thumbnailUrl; // Novo campo

    private Integer pageCount; // Novo campo

    private String googleBooksId; // Novo campo

    @NotNull(message = "Status é necessário.")
    private Status status;
}