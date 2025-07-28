package com.project2025.auth_2.services;

import com.project2025.auth_2.converters.LoanConverter;
import com.project2025.auth_2.domain.book.Book;
import com.project2025.auth_2.domain.loan.LoanCreateDTO;
import com.project2025.auth_2.domain.loan.Loan;
import com.project2025.auth_2.domain.loan.LoanResponseDTO;
import com.project2025.auth_2.domain.user.User;
import com.project2025.auth_2.events.LoanCreatedEvent;
import com.project2025.auth_2.exception.BusinessException;
import com.project2025.auth_2.exception.ErrorCode;
import com.project2025.auth_2.repositories.BookRepository;
import com.project2025.auth_2.repositories.LoanRepository;
import com.project2025.auth_2.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service responsável pelo gerenciamento de empréstimos de livros no sistema.
 * Inclui operações como registro, devolução, cancelamento e consulta de empréstimos.
 */
@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanConverter loanConverter;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public LoanService(
            LoanRepository loanRepository,
            LoanConverter loanConverter,
            BookRepository bookRepository,
            UserRepository userRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.loanRepository = loanRepository;
        this.loanConverter = loanConverter;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    // ===== OPERAÇÕES DE EMPRÉSTIMO =====

    /**
     * Registra um novo empréstimo de livro, realizando validações e publicando evento.
     *
     * @param loanCreateDTO dados do novo empréstimo
     * @return ID do empréstimo criado
     */
    @Transactional
    @Operation(description = "Registra um novo empréstimo")
    public LoanResponseDTO registerLoan(LoanCreateDTO loanCreateDTO) {
        Book book = validateBookAvailability(loanCreateDTO.bookId());
        User user = validateUserForLoan(loanCreateDTO.userId());

        book.borrow(); // marca como emprestado

        Loan loan = prepareLoanEntity(loanCreateDTO, user, book);
        bookRepository.save(book);

        var savedLoan = loanRepository.save(loan);

        publishLoanRegisteredEvent(savedLoan.getId());
        return loanConverter.toDto(savedLoan);
    }

    /**
     * Marca um empréstimo como devolvido.
     *
     * @param loanId identificador do empréstimo
     */
    @Transactional
    @Operation(description = "Devolve um empréstimo")
    public void returnLoan(Long loanId) {
        var loan = validateLoan(loanId);

        loan.markAsReturned(); // marca como devolvido

        Book book = loan.getBook();
        book.toReturn(); // torna disponível

        bookRepository.save(book);
        loanRepository.save(loan);
    }

    /**
     * Cancela um empréstimo pendente (não devolvido).
     *
     * @param loanId ID do empréstimo
     */
    @Transactional
    @Operation(description = "Cancela um empréstimo pendente")
    public void cancelLoan(Long loanId) {
        var loan = validateLoan(loanId);

        if (loan.isPending()) {
            Book book = loan.getBook();
            book.toReturn(); // devolve o livro
            bookRepository.save(book);
            loanRepository.delete(loan);
        } else {
            throw new BusinessException("Empréstimo devolvido não pode ser cancelado", ErrorCode.INVALID_OPERATION);
        }
    }

    /**
     * Lista todos os empréstimos ativos (ainda não devolvidos).
     *
     * @return lista de empréstimos ativos
     */
    @Operation(description = "Lista todos os empréstimos ativos")
    public List<LoanResponseDTO> findActiveLoans() {
        return loanRepository.findByReturnedFalse()
                .stream()
                .map(loanConverter::toDto)
                .toList();
    }

    /**
     * Lista empréstimos vencidos (não devolvidos e com data de retorno expirada).
     *
     * @return lista de empréstimos em atraso
     */
    @Operation(description = "Lista empréstimos vencidos")
    public List<LoanResponseDTO> findOverDueLoans() {
        return loanRepository.findByReturnDateBeforeAndReturnedFalse(LocalDateTime.now())
                .stream()
                .map(loanConverter::toDto)
                .toList();
    }

    /**
     * Busca empréstimos realizados por um usuário.
     *
     * @param userId ID do usuário
     * @return lista de empréstimos
     */
    @Operation(description = "Busca empréstimos por usuário")
    public List<LoanResponseDTO> findLoansByUser(Long userId) {
        validateUserForLoan(userId);
        return loanRepository.findAllByUserId(userId)
                .stream()
                .map(loanConverter::toDto)
                .toList();
    }

    /**
     * Busca um empréstimo pelo ID.
     *
     * @param loanId ID do empréstimo
     * @return empréstimo encontrado
     */
    @Operation(description = "Busca um empréstimo por ID")
    public LoanResponseDTO findLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .map(loanConverter::toDto)
                .orElseThrow(() -> new BusinessException("Empréstimo não encontrado", ErrorCode.LOAN_NOT_FOUND));
    }

    // ===== VALIDAÇÕES =====

    /**
     * Valida se o usuário está ativo e pode realizar empréstimo.
     *
     * @param userId ID do usuário
     * @return usuário válido
     */
    private User validateUserForLoan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));

        if (!user.isActive()) {
            throw new BusinessException("Usuário inativo não faz empréstimo", ErrorCode.USER_UNAVAILABLE);
        }

        return user;
    }

    /**
     * Valida se o livro está disponível para empréstimo.
     *
     * @param bookId ID do livro
     * @return livro disponível
     */
    private Book validateBookAvailability(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessException("Livro não encontrado", ErrorCode.BOOK_NOT_FOUND));

        boolean bookLoaned = loanRepository.existsByBookAndReturnedFalse(book);
        if (bookLoaned) {
            throw new BusinessException("Livro já emprestado", ErrorCode.BOOK_UNAVAILABLE);
        }

        return book;
    }

    /**
     * Valida se um empréstimo existe e ainda está pendente.
     *
     * @param loanId ID do empréstimo
     * @return empréstimo válido
     */
    private Loan validateLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new BusinessException("Empréstimo não encontrado", ErrorCode.LOAN_NOT_FOUND));

        if (loan.isReturned()) {
            throw new BusinessException("Este empréstimo já foi devolvido", ErrorCode.LOAN_ALREADY_RETURNED);
        }

        return loan;
    }

    // ===== EVENTOS =====

    /**
     * Publica um evento indicando que um empréstimo foi registrado.
     *
     * @param loanId ID do empréstimo
     */
    private void publishLoanRegisteredEvent(Long loanId) {
        eventPublisher.publishEvent(new LoanCreatedEvent(loanId));
    }

    // ===== CONSTRUÇÃO DO EMPRÉSTIMO =====

    /**
     * Prepara a entidade de empréstimo com base nos dados do DTO e entidades relacionadas.
     *
     * @param loanCreateDTO dados do novo empréstimo
     * @param user          usuário
     * @param book          livro
     * @return entidade Loan pronta para persistência
     */
    private Loan prepareLoanEntity(LoanCreateDTO loanCreateDTO, User user, Book book) {
        Loan loan = loanConverter.toEntity(loanCreateDTO);
        loan.setUser(user);
        loan.setBook(book);

        loan.setLoanDate(LocalDateTime.now());
        loan.setReturnDate(loan.getLoanDate().plusDays(14));
        loan.setReturned(false);

        return loan;
    }
}
