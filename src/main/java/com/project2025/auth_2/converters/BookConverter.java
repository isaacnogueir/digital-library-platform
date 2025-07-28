package com.project2025.auth_2.converters;

import com.project2025.auth_2.domain.book.Book;
import com.project2025.auth_2.domain.book.BookResponseDTO;
import com.project2025.auth_2.domain.book.BookUpdateDTO;
import com.project2025.auth_2.domain.book.BookCreateDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter {
    public BookResponseDTO toDto(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getPublisherName(),
                book.getStatus());
    }


    public Book toEntity(BookCreateDTO dto) {
        return Book.builder()
                .title(dto.title())
                .author(dto.author())
                .isbn(dto.isbn())
                .publicationYear(dto.publicationYear())
                .publisherName(dto.publisherName())
                .status(dto.status())
                .build();
    }


    public List<BookResponseDTO> converterList(List<Book> books) {
        return books.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public void updateFromDto(Book book, BookUpdateDTO dto) {
        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setIsbn(dto.isbn());
        book.setPublicationYear(dto.publicationYear());
        book.setPublisherName(dto.publisherName());
    }
}

