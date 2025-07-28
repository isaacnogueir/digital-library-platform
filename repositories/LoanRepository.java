package com.project2025.auth_2.repositories;

import com.project2025.auth_2.domain.book.Book;
import com.project2025.auth_2.domain.loan.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // ===== BUSCAS POR STATUS DO EMPRÉSTIMO =====

    /**
     * Busca todos os empréstimos não devolvidos
     *
     * @return lista de empréstimos em aberto
     */
    List<Loan> findByReturnedFalse();

    /**
     * Verifica se um livro está emprestado e ainda não foi devolvido
     *
     * @param book livro para verificar
     * @return true se o livro estiver emprestado e não devolvido
     */
    boolean existsByBookAndReturnedFalse(Book book);

    /**
     * Busca todos os empréstimos vencidos (data de devolução passou e ainda não foi devolvido)
     *
     * @param date data de referência
     * @return lista de empréstimos vencidos
     */
    List<Loan> findByReturnDateBeforeAndReturnedFalse(LocalDateTime date);

    // ===== BUSCAS POR USUÁRIO =====

    /**
     * Busca empréstimos de um usuário específico (em aberto ou não)
     *
     * @param id ID do usuário
     * @return lista de empréstimos do usuário
     */
    List<Loan> findByUserId(Long id);

    /**
     * Busca todos os empréstimos (inclusive devolvidos) feitos por um usuário
     *
     * @param userId ID do usuário
     * @return lista de empréstimos
     */
    List<Loan> findAllByUserId(Long userId);

    // ===== BUSCAS POR COMBINAÇÕES DE CAMPOS =====

    /**
     * Busca um empréstimo por livro e usuário
     *
     * @param bookId ID do livro
     * @param userId ID do usuário
     * @return empréstimo correspondente, se existir
     */
    Optional<Loan> findByBookIdAndUserId(Long bookId, Long userId);
}
