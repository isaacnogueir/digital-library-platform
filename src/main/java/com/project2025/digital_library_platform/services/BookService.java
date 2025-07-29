package com.project2025.digital_library_platform.services;


import com.project2025.digital_library_platform.converters.BookConverter;
import com.project2025.digital_library_platform.domain.book.*;
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
     *
     * @param bookCreateDTO título do livro
     * @return BookResponseDTO com os dados do livro criado
     */
    @Transactional
    @Operation(description = "Cria um novo livro no sistema")
    public BookResponseDTO createBook(BookCreateDTO bookCreateDTO) {
        validateBookCreation(bookCreateDTO);

        Book book = bookConverter.toEntity(bookCreateDTO)
                .withStatus(bookCreateDTO.status());

        book.activate();

        Book savedBook = bookRepository.save(book);

        publishUserRegisteredEvent(savedBook.getId());
        return bookConverter.toDto(savedBook);
    }

    /**
     * Atualiza um livro existente
     *
     * @param bookUpdateDTO dados para atualização
     * @return BookResponseDTO com os dados atualizados
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
     *
     * @param id identificador do livro
     * @return BookResponseDTO com os dados do livro
     */
    @Operation(description = "Busca um livro por ID")
    public BookResponseDTO findById(Long id) {
        var book = findBookById(id);
        return bookConverter.toDto(book);
    }

    @Operation(description = "Buscar um livro por Isbn")
    public BookResponseDTO findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
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
     *
     * @return lista de BookResponseDTO
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
     *
     * @return lista de BookResponseDTO disponíveis
     */
    @Operation(description = "Lista todos os livros disponíveis para empréstimo")
    public List<BookResponseDTO> findAvailableBooks() {
        List<Book> availableBooks = bookRepository.findByActiveAndStatus(true, Status.AVAILABLE);
        return bookConverter.converterList(availableBooks);
    }

    // ===== MÉTODOS DE VALIDAÇÃO =====

    /**
     * Valida se um livro pode ser criado (ISBN e título únicos)
     *
     * @param bookCreateDTO do createDTO título do livro
     */
    private void validateBookCreation(BookCreateDTO bookCreateDTO) {
        if (existsByIsbn(bookCreateDTO.isbn())) {
            throw new BusinessException("Livro com este ISBN já cadastrado!", ErrorCode.BOOK_ALREADY_EXISTS);
        }

        if (existsByTitle(bookCreateDTO.title())) {
            throw new BusinessException("Livro com este título já cadastrado!", ErrorCode.BOOK_ALREADY_EXISTS);
        }
    }

    /**
     * Valida se um livro pode ser atualizado
     *
     * @param existingBook  livro existente
     * @param bookUpdateDTO dados para atualização
     */
    private void validateBookUpdate(Book existingBook, BookUpdateDTO bookUpdateDTO) {
        // Valida ISBN apenas se foi alterado
        if (!existingBook.getIsbn().equals(bookUpdateDTO.isbn()) && existsByIsbn(bookUpdateDTO.isbn())) {
            throw new BusinessException("ISBN já está em uso por outro livro!", ErrorCode.BOOK_ALREADY_EXISTS);
        }

        // Valida título apenas se foi alterado
        if (!existingBook.getTitle().equals(bookUpdateDTO.title()) && existsByTitle(bookUpdateDTO.title())) {
            throw new BusinessException("Título já está em uso por outro livro!", ErrorCode.BOOK_ALREADY_EXISTS);
        }
    }

    // ===== MÉTODOS AUXILIARES =====

    /**
     * Busca um livro por ID ou lança exceção se não encontrado
     *
     * @param id identificador do livro
     * @return Book encontrado
     */
    private Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Livro não encontrado!", ErrorCode.BOOK_NOT_FOUND));
    }

    /**
     * Verifica se existe um livro com o ISBN informado
     *
     * @param isbn código ISBN para verificação
     * @return true se existe, false caso contrário
     */
    private boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    /**
     * Verifica se existe um livro com o título informado
     *
     * @param title título para verificação
     * @return true se existe, false caso contrário
     */
    private boolean existsByTitle(String title) {
        return bookRepository.existsByTitle(title);
    }

    /**
     * Publica evento de livro registrado
     *
     * @param bookId ID do usuário registrado
     */
    private void publishUserRegisteredEvent(Long bookId) {
        eventPublisher.publishEvent(new BookCreatedEvent(bookId));
    }
}
