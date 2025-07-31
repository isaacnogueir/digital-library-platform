package com.project2025.digital_library_platform.converters;

import com.project2025.digital_library_platform.domain.book.Book;
import com.project2025.digital_library_platform.domain.book.dtos.BookResponseDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookUpdateDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookCreateDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookConverter {

    public BookResponseDTO toDto(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setGoogleBooksId(book.getGoogleBooksId());
        dto.setTitle(book.getTitle());
        dto.setAuthors(book.getAuthors());
        dto.setPublisher(book.getPublisher());
        dto.setPublishedDate(book.getPublishedDate());
        dto.setIsbn10(book.getIsbn10());
        dto.setIsbn13(book.getIsbn13());
        dto.setDescription(book.getDescription());
        dto.setThumbnailUrl(book.getThumbnailUrl());
        dto.setPageCount(book.getPageCount());
        dto.setStatus(book.getStatus());
        dto.setActive(book.isActive());
        return dto;
    }

    public Book toEntity(BookCreateDTO dto) {
        return Book.builder()
                .title(dto.title())
                .authors(dto.authors()) // ← mudou de author para authors
                .publisher(dto.publisher()) // ← mudou de publisherName para publisher
                .publishedDate(dto.publishedDate()) // ← mudou de publicationYear para publishedDate
                .isbn10(dto.isbn10()) // ← agora são separados
                .isbn13(dto.isbn13())
                .description(dto.description()) // ← campo novo
                .thumbnailUrl(dto.thumbnailUrl()) // ← campo novo
                .pageCount(dto.pageCount()) // ← campo novo
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
        book.setAuthors(dto.authors()); // ← mudou de setAuthor para setAuthors
        book.setPublisher(dto.publisher()); // ← mudou de setPublisherName para setPublisher
        book.setPublishedDate(dto.publishedDate()); // ← mudou de setPublicationYear para setPublishedDate
        book.setIsbn10(dto.isbn10()); // ← agora são separados
        book.setIsbn13(dto.isbn13());
        book.setDescription(dto.description()); // ← campo novo
        book.setThumbnailUrl(dto.thumbnailUrl()); // ← campo novo
        book.setPageCount(dto.pageCount()); // ← campo novo
    }

    public BookResponseDTO googleBookToDto(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setGoogleBooksId(book.getGoogleBooksId());
        dto.setTitle(book.getTitle());
        dto.setAuthors(book.getAuthors());
        dto.setPublisher(book.getPublisher());
        dto.setPublishedDate(book.getPublishedDate());
        dto.setIsbn10(book.getIsbn10());
        dto.setIsbn13(book.getIsbn13());
        dto.setDescription(book.getDescription());
        dto.setThumbnailUrl(book.getThumbnailUrl());
        dto.setPageCount(book.getPageCount());
        dto.setStatus(book.getStatus());
        dto.setActive(book.isActive());
        return dto;
    }
}