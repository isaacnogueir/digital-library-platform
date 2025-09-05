package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.LoanConverter;
import com.project2025.digital_library_platform.entity.book.Book;
import com.project2025.digital_library_platform.DTOs.loanDtos.LoanCreateDTO;
import com.project2025.digital_library_platform.entity.Loan;

import com.project2025.digital_library_platform.DTOs.loanDtos.LoanResponseDTO;
import com.project2025.digital_library_platform.entity.user.User;
import com.project2025.digital_library_platform.events.LoanCreatedEvent;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.repositories.BookRepository;
import com.project2025.digital_library_platform.repositories.LoanRepository;
import com.project2025.digital_library_platform.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

        @Transactional
    @Operation(description = "Registra um novo empréstimo")
    public LoanResponseDTO registerLoan(LoanCreateDTO loanCreateDTO) {
        Book book = validateBookAvailability(loanCreateDTO.bookId());
        User user = validateUserForLoan(loanCreateDTO.userId());

        book.borrow(); 

        Loan loan = prepareLoanEntity(loanCreateDTO, user, book);
        bookRepository.save(book);

        var savedLoan = loanRepository.save(loan);

        eventPublisher.publishEvent(new LoanCreatedEvent(savedLoan.getId()));
        return loanConverter.toDto(savedLoan);
    }

        @Transactional
    @Operation(description = "Devolve um empréstimo")
    public void returnLoan(Long loanId) {
        var loan = validateLoan(loanId);

        loan.markAsReturned(); 

        Book book = loan.getBook();
        book.toReturn(); 

        bookRepository.save(book);
        loanRepository.save(loan);
    }

        @Transactional
    @Operation(description = "Cancela um empréstimo pendente")
    public void cancelLoan(Long loanId) {
        var loan = validateLoan(loanId);

        if (loan.isPending()) {
            Book book = loan.getBook();
            book.toReturn(); 
            bookRepository.save(book);
            loanRepository.delete(loan);
        } else {
            throw new BusinessException("Empréstimo devolvido não pode ser cancelado", ErrorCode.INVALID_OPERATION);
        }
    }

        @Operation(description = "Lista todos os empréstimos ativos")
    public List<LoanResponseDTO> findActiveLoans() {
        return loanRepository.findByReturnedFalse()
                .stream()
                .map(loanConverter::toDto)
                .toList();
    }

        @Operation(description = "Lista empréstimos vencidos")
    public List<LoanResponseDTO> findOverDueLoans() {
        return loanRepository.findByReturnDateBeforeAndReturnedFalse(LocalDateTime.now())
                .stream()
                .map(loanConverter::toDto)
                .toList();
    }

        @Operation(description = "Busca empréstimos por usuário")
    public List<LoanResponseDTO> findLoansByUser(Long userId) {
        validateUserForLoan(userId);
        return loanRepository.findAllByUserId(userId)
                .stream()
                .map(loanConverter::toDto)
                .toList();
    }

        @Operation(description = "Busca um empréstimo por ID")
    public LoanResponseDTO findLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .map(loanConverter::toDto)
                .orElseThrow(() -> new BusinessException("Empréstimo não encontrado", ErrorCode.LOAN_NOT_FOUND));
    }

        @Operation(description = "valida se o usuário esta ativo e pode fazer emprestimo")
    private User validateUserForLoan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));

        if (!user.isActive()) {
            throw new BusinessException("Usuário inativo não faz empréstimo", ErrorCode.USER_UNAVAILABLE);
        }
        return user;
    }

        @Operation(description = "Valise se o livro está disponivel para emprestimo")
    private Book validateBookAvailability(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessException("Livro não encontrado", ErrorCode.BOOK_NOT_FOUND));

        boolean bookLoaned = loanRepository.existsByBookAndReturnedFalse(book);
        if (bookLoaned) {
            throw new BusinessException("Livro já emprestado", ErrorCode.BOOK_UNAVAILABLE);
        }
        return book;
    }

        @Operation(description = "Valida se o emprestimo existe e ainda está pendendente")
    private Loan validateLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new BusinessException("Empréstimo não encontrado", ErrorCode.LOAN_NOT_FOUND));

        if (loan.isReturned()) {
            throw new BusinessException("Este empréstimo já foi devolvido", ErrorCode.LOAN_ALREADY_RETURNED);
        }
        return loan;
    }

        @Operation(description = "Publica evento no rabbit quando cria emprestimo")
    private void publishLoanRegisteredEvent(Long loanId) {
        eventPublisher.publishEvent(new LoanCreatedEvent(loanId));
    }

        @Operation(description = "Prepara para criar emprestimos")
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
