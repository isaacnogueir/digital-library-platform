package com.project2025.digital_library_platform.controllers;


import com.project2025.digital_library_platform.domain.book.dtos.BookCreateDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookResponseDTO;
import com.project2025.digital_library_platform.domain.book.dtos.BookUpdateDTO;
import com.project2025.digital_library_platform.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Management", description = "Operações relacionadas ao gerenciamento de livros.")
@SecurityRequirement(name = "bearerAuth")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ===== OPERAÇÕES DE CRIAÇÃO E ATUALIZAÇÃO =====

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo livro", description = "Cria um novo livro no sistema com status ativo e disponível.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookCreateDTO dto) {
        BookResponseDTO created = bookService.createBook(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar livro", description = "Atualiza os dados de um livro existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDTO bookUpdateDTO) {
        BookResponseDTO updated = bookService.updateBook(id, bookUpdateDTO);
        return ResponseEntity.ok(updated);
    }

    // ===== OPERAÇÕES DE BUSCA =====

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID", description = "Retorna os dados de um livro a partir do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) {
        BookResponseDTO book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Buscar livro por ISBN", description = "Retorna os dados de um livro a partir do seu ISBN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BookResponseDTO> findByIsbn(@PathVariable String isbn) {
        String normalizedIsbn = isbn.replaceAll("[-\\s]", "");
        BookResponseDTO book = bookService.findByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/title/{title}")
    @Operation(summary = "Buscar livro por titulo", description = "Retorna os dados de um livro a partir do seu titulo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<List<BookResponseDTO>> findByTitle(@PathVariable String title) {
        List<BookResponseDTO> book = bookService.findByTitle(title);
        return ResponseEntity.ok(book);
    }


    @GetMapping("/list")
    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista com todos os livros cadastrados.")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    public ResponseEntity<List<BookResponseDTO>> findAll() {
        List<BookResponseDTO> list = bookService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/available")
    @Operation(summary = "Listar livros disponíveis", description = "Retorna todos os livros com status 'disponível' e ativos.")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @ApiResponse(responseCode = "200", description = "Lista de livros disponíveis retornada com sucesso")
    public ResponseEntity<List<BookResponseDTO>> findAvailableBooks() {
        List<BookResponseDTO> books = bookService.findAvailableBooks();
        return ResponseEntity.ok(books);
    }
}