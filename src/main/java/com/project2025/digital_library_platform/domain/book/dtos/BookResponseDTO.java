package com.project2025.digital_library_platform.domain.book.dtos;

import com.project2025.digital_library_platform.domain.book.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String googleBooksId;
    private String title;
    private String authors;
    private String publisher;
    private String publishedDate;
    private String isbn10;
    private String isbn13;
    private String description;
    private Integer pageCount;
    private Status status;
    private boolean active;
}