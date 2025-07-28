package com.project2025.auth_2.domain.book;

import lombok.*;
/**
 * DTO para resposta contendo informações completas de um LIVRO.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;
    private String publisherName;
    private Status status;

}
