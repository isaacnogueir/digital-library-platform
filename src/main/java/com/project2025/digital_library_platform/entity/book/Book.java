package com.project2025.digital_library_platform.entity.book;

import com.project2025.digital_library_platform.DTOs.googleBooksDtos.GoogleBooksResponseDTO;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

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

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID ÚNICO DO LIVRO", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    // CAMPOS DO GOOGLE BOOKS

        @Column(name = "google_books_id", unique = true)
    @Schema(description = "ID DO GOOGLE BOOKS", example = "gE0YAAAAYAAJ")
    private String googleBooksId;

        @NotBlank(message = "TÍTULO É OBRIGATÓRIO.")
    @Column(name = "title", nullable = false)
    @Schema(description = "TÍTULO DO LIVRO", example = "CLEAN CODE")
    private String title;

        @Column(name = "authors", columnDefinition = "TEXT")
    @Schema(description = "AUTORES DO LIVRO (SEPARADOS POR VÍRGULA)")
    private String authors;

        @Column(name = "publisher")
    @Schema(description = "EDITORA DO LIVRO")
    private String publisher;

        @Column(name = "published_date")
    @Schema(description = "DATA DE PUBLICAÇÃO")
    private String publishedDate;

        @Column(name = "isbn_10")
    @Schema(description = "ISBN-10")
    private String isbn10;

        @Column(name = "isbn_13")
    @Schema(description = "ISBN-13")
    private String isbn13;

        @Column(name = "description", columnDefinition = "TEXT")
    @Schema(description = "DESCRIÇÃO/SINOPSE DO LIVRO")
    private String description;

        @Column(name = "thumbnail_url")
    @Schema(description = "URL DA CAPA DO LIVRO")
    private String thumbnailUrl;

        @Column(name = "page_count")
    @Schema(description = "NÚMERO DE PÁGINAS")
    private Integer pageCount;

        @With
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(description = "STATUS DO LIVRO", example = "AVAILABLE")
    private Status status = Status.AVAILABLE; 

        @With
    @Column(name = "active", nullable = false)
    @Schema(description = "INDICA SE O LIVRO ESTÁ ATIVO", example = "true")
    private boolean active = true;

    

        @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "DATA E HORA DE CRIAÇÃO DO LIVRO", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

        @Column(name = "updated_at")
    @Schema(description = "DATA E HORA DA ÚLTIMA ATUALIZAÇÃO", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    // MÉTODOS DE NEGÓCIO

        @Schema(description = "Ativa o livro e atualiza data de atualizacao")
    public Book activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

        @Schema(description = "Desativa livro")
    public Book deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

        @Schema(description = "Verifica se livro está disponivel")
    public boolean isAvailable() {
        return this.active && this.status == Status.AVAILABLE;
    }

        @Schema(description = "Empresta livro")
    public void borrow() {
        if (!isAvailable()) {
            throw new BusinessException("Livro não disponível", ErrorCode.BOOK_UNAVAILABLE);
        }
        this.status = Status.LOANED;
    }

        @Schema(description = "Devolve livro")
    public void toReturn() {
        if (this.status != Status.LOANED) {
            throw new BusinessException("O livro não pode ser devolvido pois não está emprestado", ErrorCode.BOOK_UNAVAILABLE);
        }
        this.status = Status.AVAILABLE;
    }

        @Schema(description = "Marca como indisponivel")
    public void markAsUnavailable() {
        this.status = Status.UNAVAILABLE;
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

        @Schema(description = "Verifica se está emprestado")
    public boolean isLoaned() {
        return this.status == Status.LOANED;
    }

        @Schema(description = "Verifica se está indiposivel ou inativo")
    public boolean isUnavailable() {
        return !this.active || this.status == Status.UNAVAILABLE;
    }

     //===== MÉTODO PARA POPULAR COM DADOS DO GOOGLE BOOKS =====

        @Schema(description = "Popula campos da integração com GoogleBooks")
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

        
        if (volumeInfo.getIndustryIdentifiers() != null) {
            for (var identifier : volumeInfo.getIndustryIdentifiers()) {
                if ("ISBN_10".equals(identifier.getType())) {
                    this.isbn10 = identifier.getIdentifier();
                } else if ("ISBN_13".equals(identifier.getType())) {
                    this.isbn13 = identifier.getIdentifier();
                }
            }
        }
        this.active = true;
        this.status = Status.AVAILABLE;
    }

     // MÉTODOS DE AUDITORIA

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