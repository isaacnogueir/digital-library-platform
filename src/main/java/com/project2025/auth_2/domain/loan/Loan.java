package com.project2025.auth_2.domain.loan;

import com.project2025.auth_2.domain.book.Book;
import com.project2025.auth_2.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ENTIDADE QUE REPRESENTA UM EMPRÉSTIMO NO SISTEMA.
 */
@Entity
@Table(name = "loans", indexes = {
        @Index(name = "idx_loan_user_id", columnList = "user_id"),
        @Index(name = "idx_loan_book_id", columnList = "book_id"),
        @Index(name = "idx_loan_returned", columnList = "returned")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    /**
     * IDENTIFICADOR ÚNICO DO EMPRÉSTIMO.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID ÚNICO DO EMPRÉSTIMO", example = "1")
    private Long id;

    /**
     * DATA E HORA DO EMPRÉSTIMO.
     */
    @Column(name = "loan_date", nullable = false, updatable = false)
    @Schema(description = "Data e hora em que o empréstimo foi realizado", example = "2025-07-25T10:00:00")
    private LocalDateTime loanDate;

    /**
     * DATA E HORA ESTIMADA DE DEVOLUÇÃO DO LIVRO.
     */
    @Column(name = "return_date", nullable = false)
    @Schema(description = "Data e hora prevista para devolução", example = "2025-08-05T10:00:00")
    private LocalDateTime returnDate;

    /**
     * INDICA SE O LIVRO JÁ FOI DEVOLVIDO.
     */
    @Column(name = "returned", nullable = false)
    @Schema(description = "Status de devolução do livro", example = "false")
    private boolean returned;

    /**
     * DATA E HORA REAL DA DEVOLUÇÃO DO LIVRO.
     */
    @Column(name = "actual_return_date")
    @Schema(description = "Data e hora em que o livro foi efetivamente devolvido", example = "2025-08-05T15:30:00")
    private LocalDateTime actualReturnDate;

    /**
     * USUÁRIO QUE REALIZOU O EMPRÉSTIMO.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Usuário que realizou o empréstimo")
    private User user;

    /**
     * LIVRO QUE FOI EMPRESTADO.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @Schema(description = "Livro que foi emprestado")
    private Book book;

    /**
     * DATA DE CRIAÇÃO DO REGISTRO.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Data e hora de criação do empréstimo", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    /**
     * DATA DA ÚLTIMA ATUALIZAÇÃO DO REGISTRO.
     */
    @Column(name = "updated_at")
    @Schema(description = "Data da última atualização", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    /**
     * Define as datas de criação e atualização ao persistir.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.loanDate == null) this.loanDate = LocalDateTime.now();
    }

    /**
     * Atualiza o campo `updatedAt` automaticamente.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Marca o empréstimo como devolvido, atualizando a data real da devolução.
     */
    public void markAsReturned() {
        this.returned = true;
        this.actualReturnDate = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Verifica se o empréstimo ainda está pendente.
     */
    public boolean isPending() {
        return !this.returned;
    }
}
