package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.BookConverter;
import com.project2025.digital_library_platform.domain.book.*;
import com.project2025.digital_library_platform.domain.book.dtos.BookCreateDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookResponseDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookUpdateDTO;
import com.project2025.digital_library_platform.events.BookCreatedEvent;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.repositories.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookConverter bookConverter;
    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookService(BookConverter bookConverter, BookRepository bookRepository, ApplicationEventPublisher eventPublisher) {
        this.bookConverter = bookConverter;
        this.bookRepository = bookRepository;
        this.eventPublisher = eventPublisher;
    }

    // ===== OPERAÇÕES CRUD =====

    /**
     * Cria um novo livro no sistema
     */
    @Transactional
    @Operation(description = "Cria um novo livro no sistema")
    public BookResponseDTO createBook(BookCreateDTO bookCreateDTO) {
        validateBookCreation(bookCreateDTO);

        Book book = bookConverter.toEntity(bookCreateDTO);
        book.activate();

        Book savedBook = bookRepository.save(book);

        publishBookCreatedEvent(savedBook.getId());
        return bookConverter.toDto(savedBook);
    }

    /**
     * Atualiza um livro existente
     */
    @Transactional
    @Operation(description = "Atualiza um livro existente")
    public BookResponseDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        var existingBook = findBookById(id);
        validateBookUpdate(existingBook, bookUpdateDTO);
        bookConverter.updateFromDto(existingBook, bookUpdateDTO);

        var updatedBook = bookRepository.save(existingBook);
        return bookConverter.toDto(updatedBook);
    }

    // ===== OPERAÇÕES DE CONSULTA =====

    /**
     * Busca um livro por ID
     */
    @Operation(description = "Busca um livro por ID")
    public BookResponseDTO findById(Long id) {
        var book = findBookById(id);
        return bookConverter.toDto(book);
    }

    // CORRIGIDO - Agora busca por ISBN-10 OU ISBN-13
    @Operation(description = "Buscar um livro por ISBN")
    public BookResponseDTO findByIsbn(String isbn) {
        return bookRepository.findByIsbn10OrIsbn13(isbn, isbn)
                .map(bookConverter::toDto)
                .orElseThrow(() -> new BusinessException("Livro com ISBN não encontrado", ErrorCode.BOOK_NOT_FOUND));
    }

    @Operation(description = "Buscar por título")
    public BookResponseDTO findByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .map(bookConverter::toDto)
                .orElseThrow(() -> new BusinessException("Livro não encontrado", ErrorCode.BOOK_NOT_FOUND));
    }

    /**
     * Lista todos os livros cadastrados
     */
    @Operation(description = "Lista todos os livros cadastrados")
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookConverter::toDto)
                .toList();
    }

    /**
     * Lista todos os livros disponíveis para empréstimo
     */
    @Operation(description = "Lista todos os livros disponíveis para empréstimo")
    public List<BookResponseDTO> findAvailableBooks() {
        List<Book> availableBooks = bookRepository.findByActiveAndStatus(true, Status.AVAILABLE);
        return bookConverter.converterList(availableBooks);
    }

    // ===== MÉTODOS DE VALIDAÇÃO CORRIGIDOS =====

    /**
     * Valida se um livro pode ser criado
     */
    private void validateBookCreation(BookCreateDTO bookCreateDTO) {
        // Valida ISBN-10 se fornecido
        if (bookCreateDTO.isbn10() != null && !bookCreateDTO.isbn10().isEmpty()) {
            if (existsByIsbn10(bookCreateDTO.isbn10())) {
                throw new BusinessException("Livro com este ISBN-10 já cadastrado!", ErrorCode.BOOK_ALREADY_EXISTS);
            }
        }

        // Valida ISBN-13 se fornecido
        if (bookCreateDTO.isbn13() != null && !bookCreateDTO.isbn13().isEmpty()) {
            if (existsByIsbn13(bookCreateDTO.isbn13())) {
                throw new BusinessException("Livro com este ISBN-13 já cadastrado!", ErrorCode.BOOK_ALREADY_EXISTS);
            }
        }

        // Valida título
        if (existsByTitle(bookCreateDTO.title())) {
            throw new BusinessException("Livro com este título já cadastrado!", ErrorCode.BOOK_ALREADY_EXISTS);
        }

        // Valida Google Books ID se fornecido
        if (bookCreateDTO.googleBooksId() != null && !bookCreateDTO.googleBooksId().isEmpty()) {
            if (existsByGoogleBooksId(bookCreateDTO.googleBooksId())) {
                throw new BusinessException("Livro já cadastrado através do Google Books!", ErrorCode.BOOK_ALREADY_EXISTS);
            }
        }
    }

    /**
     * Valida se um livro pode ser atualizado
     */
    private void validateBookUpdate(Book existingBook, BookUpdateDTO bookUpdateDTO) {
        // Valida ISBN-10 apenas se foi alterado
        if (bookUpdateDTO.isbn10() != null &&
                !bookUpdateDTO.isbn10().equals(existingBook.getIsbn10()) &&
                existsByIsbn10(bookUpdateDTO.isbn10())) {
            throw new BusinessException("ISBN-10 já está em uso por outro livro!", ErrorCode.BOOK_ALREADY_EXISTS);
        }

        // Valida ISBN-13 apenas se foi alterado
        if (bookUpdateDTO.isbn13() != null &&
                !bookUpdateDTO.isbn13().equals(existingBook.getIsbn13()) &&
                existsByIsbn13(bookUpdateDTO.isbn13())) {
            throw new BusinessException("ISBN-13 já está em uso por outro livro!", ErrorCode.BOOK_ALREADY_EXISTS);
        }

        // Valida título apenas se foi alterado
        if (!existingBook.getTitle().equals(bookUpdateDTO.title()) && existsByTitle(bookUpdateDTO.title())) {
            throw new BusinessException("Título já está em uso por outro livro!", ErrorCode.BOOK_ALREADY_EXISTS);
        }
    }

    // ===== MÉTODOS AUXILIARES CORRIGIDOS =====

    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Livro não encontrado!", ErrorCode.BOOK_NOT_FOUND));
    }

    // NOVOS MÉTODOS - Para ISBN separados
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

    private void publishBookCreatedEvent(Long bookId) {
        eventPublisher.publishEvent(new BookCreatedEvent(bookId));
    }
}