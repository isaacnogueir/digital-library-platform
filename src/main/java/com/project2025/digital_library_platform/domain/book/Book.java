package com.project2025.digital_library_platform.domain.book;

import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
        @Index(name = "idx_book_active", columnList = "active")
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

    /**
     * TÍTULO DO LIVRO.
     * DEVE TER ENTRE 4 E 100 CARACTERES E SER ÚNICO.
     */
    @With
    @NotBlank(message = "TÍTULO É OBRIGATÓRIO.")
    @Size(min = 4, max = 100, message = "TÍTULO DEVE TER ENTRE 4 E 100 CARACTERES.")
    @Column(name = "title", unique = true, nullable = false)
    @Schema(description = "TÍTULO DO LIVRO", example = "DOM QUIXOTE")
    private String title;

    /**
     * NOME DO AUTOR DO LIVRO.
     * DEVE TER ENTRE 4 E 100 CARACTERES.
     */
    @NotBlank(message = "AUTOR É OBRIGATÓRIO.")
    @Size(min = 4, max = 100)
    @Column(name = "author", nullable = false)
    @Schema(description = "NOME DO AUTOR DO LIVRO", example = "MIGUEL DE CERVANTES")
    private String author;

    /**
     * CÓDIGO ISBN DO LIVRO.
     * DEVE SER ÚNICO E OBRIGATÓRIO.
     */
    @NotBlank(message = "ISBN É OBRIGATÓRIO.")
    @Column(name = "isbn", unique = true, nullable = false, length = 100)
    @Schema(description = "ISBN DO LIVRO", example = "978-3-16-148410-0")
    private String isbn;

    /**
     * ANO DE PUBLICAÇÃO DO LIVRO.
     * OBRIGATÓRIO.
     */
    @NotNull(message = "ANO DE PUBLICAÇÃO É OBRIGATÓRIO.")
    @Column(name = "publication_year")
    @Schema(description = "ANO DE PUBLICAÇÃO DO LIVRO", example = "1605")
    private Integer publicationYear;

    /**
     * NOME DA EDITORA DO LIVRO.
     * DEVE TER ENTRE 5 E 100 CARACTERES.
     */
    @NotBlank(message = "NOME DA EDITORA É OBRIGATÓRIO.")
    @Column(name = "publisher_name")
    @Size(min = 5, max = 100)
    @Schema(description = "NOME DA EDITORA DO LIVRO", example = "EDITORA MODERNA")
    private String publisherName;

    /**
     * STATUS ATUAL DO LIVRO (AVAILABLE, LOANED, UNAVAILABLE).
     * OBRIGATÓRIO.
     */
    @With
    @NotNull(message = "STATUS É OBRIGATÓRIO.")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(description = "STATUS DO LIVRO", example = "AVAILABLE")
    private Status status;

    /**
     * INDICADOR SE O LIVRO ESTÁ ATIVO NO SISTEMA.
     * PADRÃO É TRUE.
     */
    @With
    @Column(name = "active", nullable = false)
    @Schema(description = "INDICA SE O LIVRO ESTÁ ATIVO", example = "true")
    private boolean active = true;

    // CAMPOS DE AUDITORIA

    /**
     * DATA E HORA DE CRIAÇÃO DO REGISTRO DO LIVRO.
     * NÃO PODE SER ATUALIZADO MANUALMENTE.
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


    // MÉTODOS PARA CONTROLE DE STATUS E ATIVIDADE DO LIVRO

    /**
     * ATIVA O LIVRO (SETA ACTIVE = TRUE) E ATUALIZA A DATA DE ATUALIZAÇÃO.
     *
     * @return A PRÓPRIA INSTÂNCIA DO LIVRO ATUALIZADO
     */
    public Book activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    /**
     * DESATIVA O LIVRO (SETA ACTIVE = FALSE) E ATUALIZA A DATA DE ATUALIZAÇÃO.
     *
     * @return A PRÓPRIA INSTÂNCIA DO LIVRO ATUALIZADO
     */
    public Book deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    /**
     * VERIFICA SE O LIVRO ESTÁ DISPONÍVEL (ATIVO E STATUS AVAILABLE).
     *
     * @return TRUE SE DISPONÍVEL, FALSE CASO CONTRÁRIO
     */
    public boolean isAvailable() {
        return this.active && this.status == Status.AVAILABLE;
    }

    /**
     * Realiza a ação de emprestar o livro.
     * Valida se o livro está disponível antes de alterar seu estado.
     * Lança uma exceção se o livro não puder ser emprestado.
     */
    public void borrow() {
        if (!isAvailable()) {
            throw new BusinessException("Livro não disponível", ErrorCode.BOOK_UNAVAILABLE);
        }
        this.status = Status.LOANED;
    }

    /**
     * Realiza a ação de devolver um livro.
     * Valida se o livro estava de fato emprestado.
     */
    public void toReturn() {
        if (this.status != Status.LOANED) {
            throw new BusinessException("O livro não pode ser devolvido pois nao está emprestado", ErrorCode.BOOK_UNAVAILABLE);
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
     *
     * @return TRUE SE EMPRESTADO, FALSE CASO CONTRÁRIO
     */
    public boolean isLoaned() {
        return this.status == Status.LOANED;
    }

    /**
     * VERIFICA SE O LIVRO ESTÁ INDISPONÍVEL OU INATIVO.
     *
     * @return TRUE SE INDISPONÍVEL, FALSE CASO CONTRÁRIO
     */
    public boolean isUnavailable() {
        return !this.active || this.status == Status.UNAVAILABLE;
    }

    /**
     * METODO EXECUTADO ANTES DE PERSISTIR O LIVRO NO BANCO.
     * DEFINE AS DATAS DE CRIAÇÃO E ATUALIZAÇÃO.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * METODO EXECUTADO ANTES DE ATUALIZAR O LIVRO NO BANCO.
     * ATUALIZA A DATA DE ATUALIZAÇÃO.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
