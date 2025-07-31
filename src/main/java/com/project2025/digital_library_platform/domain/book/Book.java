package com.project2025.digital_library_platform.domain.book;

import com.project2025.digital_library_platform.domain.book.googleBooks.GoogleBooksResponseDTO;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * ENTIDADE QUE REPRESENTA UM LIVRO NO SISTEMA.
 * CONTÉM INFORMAÇÕES SOBRE TÍTULO, AUTOR, ISBN, STATUS, ENTRE OUTROS.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "books", indexes = {
        @Index(name = "idx_book_title", columnList = "title"),
        @Index(name = "idx_book_status", columnList = "status"),
        @Index(name = "idx_book_active", columnList = "active"),
        @Index(name = "idx_book_google_id", columnList = "google_books_id")
})
@Entity
@Schema(description = "REPRESENTA UM LIVRO COM INFORMAÇÕES DETALHADAS")
public class Book {

    /**
     * IDENTIFICADOR ÚNICO DO LIVRO (CHAVE PRIMÁRIA).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID ÚNICO DO LIVRO", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    // ===== CAMPOS DO GOOGLE BOOKS =====

    /**
     * ID DO GOOGLE BOOKS PARA SINCRONIZAÇÃO
     */
    @Column(name = "google_books_id", unique = true)
    @Schema(description = "ID DO GOOGLE BOOKS", example = "gE0YAAAAYAAJ")
    private String googleBooksId;

    /**
     * TÍTULO DO LIVRO (DO GOOGLE BOOKS)
     */
    @NotBlank(message = "TÍTULO É OBRIGATÓRIO.")
    @Column(name = "title", nullable = false)
    @Schema(description = "TÍTULO DO LIVRO", example = "CLEAN CODE")
    private String title;

    /**
     * AUTORES DO LIVRO (SEPARADOS POR VÍRGULA)
     */
    @Column(name = "authors", columnDefinition = "TEXT")
    @Schema(description = "AUTORES DO LIVRO (SEPARADOS POR VÍRGULA)")
    private String authors;

    /**
     * EDITORA DO LIVRO
     */
    @Column(name = "publisher")
    @Schema(description = "EDITORA DO LIVRO")
    private String publisher;

    /**
     * DATA DE PUBLICAÇÃO (FORMATO GOOGLE BOOKS)
     */
    @Column(name = "published_date")
    @Schema(description = "DATA DE PUBLICAÇÃO")
    private String publishedDate;

    /**
     * ISBN-10 DO LIVRO
     */
    @Column(name = "isbn_10")
    @Schema(description = "ISBN-10")
    private String isbn10;

    /**
     * ISBN-13 DO LIVRO
     */
    @Column(name = "isbn_13")
    @Schema(description = "ISBN-13")
    private String isbn13;

    /**
     * DESCRIÇÃO/SINOPSE DO LIVRO
     */
    @Column(name = "description", columnDefinition = "TEXT")
    @Schema(description = "DESCRIÇÃO/SINOPSE DO LIVRO")
    private String description;

    /**
     * URL DA CAPA DO LIVRO
     */
    @Column(name = "thumbnail_url")
    @Schema(description = "URL DA CAPA DO LIVRO")
    private String thumbnailUrl;

    /**
     * NÚMERO DE PÁGINAS DO LIVRO
     */
    @Column(name = "page_count")
    @Schema(description = "NÚMERO DE PÁGINAS")
    private Integer pageCount;

    // ===== CAMPOS ESPECÍFICOS DA BIBLIOTECA =====

    /**
     * STATUS ATUAL DO LIVRO (AVAILABLE, LOANED, UNAVAILABLE).
     */
    @With
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(description = "STATUS DO LIVRO", example = "AVAILABLE")
    private Status status = Status.AVAILABLE; // Padrão AVAILABLE

    /**
     * INDICADOR SE O LIVRO ESTÁ ATIVO NO SISTEMA.
     */
    @With
    @Column(name = "active", nullable = false)
    @Schema(description = "INDICA SE O LIVRO ESTÁ ATIVO", example = "true")
    private boolean active = true;

    // ===== CAMPOS DE AUDITORIA =====

    /**
     * DATA E HORA DE CRIAÇÃO DO REGISTRO DO LIVRO.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "DATA E HORA DE CRIAÇÃO DO LIVRO", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    /**
     * DATA E HORA DA ÚLTIMA ATUALIZAÇÃO DO REGISTRO DO LIVRO.
     */
    @Column(name = "updated_at")
    @Schema(description = "DATA E HORA DA ÚLTIMA ATUALIZAÇÃO", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    // ===== MÉTODOS DE NEGÓCIO =====

    /**
     * ATIVA O LIVRO (SETA ACTIVE = TRUE) E ATUALIZA A DATA DE ATUALIZAÇÃO.
     */
    public Book activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    /**
     * DESATIVA O LIVRO (SETA ACTIVE = FALSE) E ATUALIZA A DATA DE ATUALIZAÇÃO.
     */
    public Book deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    /**
     * VERIFICA SE O LIVRO ESTÁ DISPONÍVEL (ATIVO E STATUS AVAILABLE).
     */
    public boolean isAvailable() {
        return this.active && this.status == Status.AVAILABLE;
    }

    /**
     * REALIZA A AÇÃO DE EMPRESTAR O LIVRO.
     */
    public void borrow() {
        if (!isAvailable()) {
            throw new BusinessException("Livro não disponível", ErrorCode.BOOK_UNAVAILABLE);
        }
        this.status = Status.LOANED;
    }

    /**
     * REALIZA A AÇÃO DE DEVOLVER UM LIVRO.
     */
    public void toReturn() {
        if (this.status != Status.LOANED) {
            throw new BusinessException("O livro não pode ser devolvido pois não está emprestado", ErrorCode.BOOK_UNAVAILABLE);
        }
        this.status = Status.AVAILABLE;
    }

    /**
     * MARCA O LIVRO COMO INDISPONÍVEL E DESATIVA.
     */
    public void markAsUnavailable() {
        this.status = Status.UNAVAILABLE;
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * VERIFICA SE O LIVRO ESTÁ EMPRESTADO.
     */
    public boolean isLoaned() {
        return this.status == Status.LOANED;
    }

    /**
     * VERIFICA SE O LIVRO ESTÁ INDISPONÍVEL OU INATIVO.
     */
    public boolean isUnavailable() {
        return !this.active || this.status == Status.UNAVAILABLE;
    }

    // ===== MÉTODO PARA POPULAR COM DADOS DO GOOGLE BOOKS =====

    /**
     * POPULA OS CAMPOS DO LIVRO COM DADOS DO GOOGLE BOOKS
     */
    public void updateFromGoogleBooks(GoogleBooksResponseDTO.GoogleBookItemDto googleBook) {
        var volumeInfo = googleBook.getVolumeInfo();

        this.googleBooksId = googleBook.getId();
        this.title = volumeInfo.getTitle();
        this.authors = volumeInfo.getAuthors() != null ?
                String.join(", ", volumeInfo.getAuthors()) : null;
        this.publisher = volumeInfo.getPublisher();
        this.publishedDate = volumeInfo.getPublishedDate();
        this.description = volumeInfo.getDescription();
        this.pageCount = volumeInfo.getPageCount();

        // Extrair ISBNs
        if (volumeInfo.getIndustryIdentifiers() != null) {
            for (var identifier : volumeInfo.getIndustryIdentifiers()) {
                if ("ISBN_10".equals(identifier.getType())) {
                    this.isbn10 = identifier.getIdentifier();
                } else if ("ISBN_13".equals(identifier.getType())) {
                    this.isbn13 = identifier.getIdentifier();
                }
            }
        }

        // Capa do livro
        if (volumeInfo.getImageLinks() != null) {
            this.thumbnailUrl = volumeInfo.getImageLinks().getThumbnail();
        }

        // Definir como ativo e disponível por padrão
        this.active = true;
        this.status = Status.AVAILABLE;
    }

    // ===== MÉTODOS DE AUDITORIA =====

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}