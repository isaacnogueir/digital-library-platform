package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.entity.book.*;
import com.project2025.digital_library_platform.DTOs.bookDtos.BookCreateDTO;
import com.project2025.digital_library_platform.DTOs.bookDtos.BookResponseDTO;
import com.project2025.digital_library_platform.DTOs.bookDtos.BookUpdateDTO;
import com.project2025.digital_library_platform.events.BookCreatedEvent;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.mappers.BookMapper;
import com.project2025.digital_library_platform.repositories.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookService(BookMapper bookMapper, BookRepository bookRepository, ApplicationEventPublisher eventPublisher) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;

    }

    //OPERAÇÕES CRUD

        @Transactional
    @Operation(description = "Cria um novo livro no sistema")
    public BookResponseDTO createBook(BookCreateDTO bookCreateDTO) {
        validateBookCreation(bookCreateDTO);

        Book book = bookMapper.toEntity(bookCreateDTO);
        book.activate();

        Book savedBook = bookRepository.save(book);

        eventPublisher.publishEvent(new BookCreatedEvent(savedBook.getId()));
        return bookMapper.toDto(savedBook);
    }

        @Transactional
    @Operation(description = "Atualiza um livro existente")
    public BookResponseDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        var existingBook = findBookById(id);
        validateBookUpdate(existingBook, bookUpdateDTO);
        bookMapper.updateFromDto(existingBook, bookUpdateDTO);

        var updatedBook = bookRepository.save(existingBook);
        return bookMapper.toDto(updatedBook);
    }
    
    //OPERAÇÕES DE CONSULTA

        @Operation(description = "Busca um livro por ID")
    public BookResponseDTO findById(Long id) {
        var book = findBookById(id);
        return bookMapper.toDto(book);
    }

    @Operation(description = "Buscar um livro por ISBN")
    public BookResponseDTO findByIsbn(String isbn) {
        return bookRepository.findByIsbn10OrIsbn13(isbn, isbn)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new BusinessException("Livro com ISBN não encontrado", ErrorCode.BOOK_NOT_FOUND));
    }

    @Operation(description = "Buscar por título")
    public List<BookResponseDTO> findByTitle(String title) {
        List<Book> livros = bookRepository.findByTitleContainingIgnoreCase(title);
        if (livros.isEmpty()) {
            throw new BusinessException("Livro não encontrado", ErrorCode.BOOK_NOT_FOUND);
        }
        return livros.stream().map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

        @Operation(description = "Lista todos os livros cadastrados")
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

        @Operation(description = "Lista todos os livros disponíveis para empréstimo")
    public List<BookResponseDTO> findAvailableBooks() {
        List<Book> availableBooks = bookRepository.findByActiveAndStatus(true, Status.AVAILABLE);
        return bookMapper.toResponseList(availableBooks);
    }

    //MÉTODOS DE VALIDAÇÃO

        private void validateBookCreation(BookCreateDTO bookCreateDTO) {

        if (bookCreateDTO.title() == null || bookCreateDTO.title().trim().isEmpty()) {
            throw new BusinessException("Título é obrigatório", ErrorCode.BOOK_ALREADY_EXISTS);
        }

        //Valida ISBN-10 se fornecido
        if (bookCreateDTO.isbn10() != null && !bookCreateDTO.isbn10().isEmpty()) {
            if (bookRepository.existsByIsbn10(bookCreateDTO.isbn10())) {
                throw new BusinessException("Livro com este ISBN-10 já cadastrado!", ErrorCode.BOOK_ALREADY_EXISTS);
            }
        }

        //Valida ISBN-13 se fornecido
        if (bookCreateDTO.isbn13() != null && !bookCreateDTO.isbn13().isEmpty()) {
            if (bookRepository.existsByIsbn13(bookCreateDTO.isbn13())) {
                throw new BusinessException("Livro com este ISBN-13 já cadastrado!", ErrorCode.BOOK_ALREADY_EXISTS);
            }
        }

         //Valida título
        if (existsByTitle(bookCreateDTO.title())) {
            throw new BusinessException("Livro com este título já cadastrado!", ErrorCode.BOOK_ALREADY_EXISTS);
        }

         //Valida Google Books ID se fornecido
        if (bookCreateDTO.googleBooksId() != null && !bookCreateDTO.googleBooksId().isEmpty()) {
            if (existsByGoogleBooksId(bookCreateDTO.googleBooksId())) {
                throw new BusinessException("Livro já cadastrado através do Google Books!", ErrorCode.BOOK_ALREADY_EXISTS);
            }
        }
    }

        private void validateBookUpdate(Book existingBook, BookUpdateDTO bookUpdateDTO) {
        //Valida ISBN-10 apenas se foi alterado
        if (bookUpdateDTO.isbn10() != null &&
                !bookUpdateDTO.isbn10().equals(existingBook.getIsbn10()) &&
                existsByIsbn10(bookUpdateDTO.isbn10())) {
            throw new BusinessException("ISBN-10 já está em uso por outro livro!", ErrorCode.BOOK_ALREADY_EXISTS);
        }

         //Valida ISBN-13 apenas se foi alterado
        if (bookUpdateDTO.isbn13() != null &&
                !bookUpdateDTO.isbn13().equals(existingBook.getIsbn13()) &&
                existsByIsbn13(bookUpdateDTO.isbn13())) {
            throw new BusinessException("ISBN-13 já está em uso por outro livro!", ErrorCode.BOOK_ALREADY_EXISTS);
        }

         //Valida título apenas se foi alterado
        if (!existingBook.getTitle().equals(bookUpdateDTO.title()) && existsByTitle(bookUpdateDTO.title())) {
            throw new BusinessException("Título já está em uso por outro livro!", ErrorCode.BOOK_ALREADY_EXISTS);
        }
    }

    //MÉTODOS AUXILIARES

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Livro não encontrado!", ErrorCode.BOOK_NOT_FOUND));
    }

    
    private boolean existsByIsbn10(String isbn10) {
        return bookRepository.existsByIsbn10(isbn10);
    }

    private boolean existsByIsbn13(String isbn13) {
        return bookRepository.existsByIsbn13(isbn13);
    }

    private boolean existsByTitle(String title) {
        return bookRepository.existsByTitle(title);
    }

    private boolean existsByGoogleBooksId(String googleBooksId) {
        return bookRepository.existsByGoogleBooksId(googleBooksId);
    }
}