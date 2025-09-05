package com.project2025.digital_library_platform.entity;

import com.project2025.digital_library_platform.entity.book.Book;
import com.project2025.digital_library_platform.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID ÚNICO DO EMPRÉSTIMO", example = "1")
    private Long id;

        @Column(name = "loan_date", nullable = false, updatable = false)
    @Schema(description = "Data e hora em que o empréstimo foi realizado", example = "2025-07-25T10:00:00")
    private LocalDateTime loanDate;

        @Column(name = "return_date", nullable = false)
    @Schema(description = "Data e hora prevista para devolução", example = "2025-08-05T10:00:00")
    private LocalDateTime returnDate;

        @Column(name = "returned", nullable = false)
    @Schema(description = "Status de devolução do livro", example = "false")
    private boolean returned;

        @Column(name = "actual_return_date")
    @Schema(description = "Data e hora em que o livro foi efetivamente devolvido", example = "2025-08-05T15:30:00")
    private LocalDateTime actualReturnDate;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Usuário que realizou o empréstimo")
    private User user;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @Schema(description = "Livro que foi emprestado")
    private Book book;

        @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Data e hora de criação do empréstimo", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

        @Column(name = "updated_at")
    @Schema(description = "Data da última atualização", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

        @PrePersist
    @Schema(description = "Define das data de criação e atualização quando persistir")
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.loanDate == null) this.loanDate = LocalDateTime.now();
    }

        @PreUpdate
    @Schema(description = "Atualiza o campo update")
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

        @Schema(description = "Marca um emprestimo como devolvido e atualiza a data real da devolucao")
    public void markAsReturned() {
        this.returned = true;
        this.actualReturnDate = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

        @Schema(description = "Verifica se o emprestimo está pendendte")
    public boolean isPending() {
        return !this.returned;
    }
}
