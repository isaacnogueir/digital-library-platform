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

    //Entity para responseDTO - RETORNA A RESPOSTA
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
        dto.setPageCount(book.getPageCount());
        dto.setStatus(book.getStatus());
        dto.setActive(book.isActive());
        return dto;
    }

    //CreateDTO para Entity - CRIA E SALVA UM NOVO OBJETO NO BANCO
    public Book toEntity(BookCreateDTO dto) {
        return Book.builder()
                .title(dto.title())
                .authors(dto.authors())
                .publisher(dto.publisher())
                .publishedDate(dto.publishedDate())
                .isbn10(dto.isbn10())
                .isbn13(dto.isbn13())
                .description(dto.description())
                .pageCount(dto.pageCount())
                .status(dto.status())
                .build();
    }

    //Uma lista de livros para uma lista de Dtos
    public List<BookResponseDTO> converterList(List<Book> books) {
        return books.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    //Entitdade e BookUpdateDTO para updatefromDto
    public void updateFromDto(Book book, BookUpdateDTO dto) {
        book.setTitle(dto.title());
        book.setAuthors(dto.authors());
        book.setPublisher(dto.publisher());
        book.setPublishedDate(dto.publishedDate());
        book.setIsbn10(dto.isbn10());
        book.setIsbn13(dto.isbn13());
        book.setDescription(dto.description());
        book.setPageCount(dto.pageCount());
    }

    //Entidade para ResponseDto
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
        dto.setPageCount(book.getPageCount());
        dto.setStatus(book.getStatus());
        dto.setActive(book.isActive());
        return dto;
    }
}