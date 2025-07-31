package com.project2025.digital_library_platform.converters;

import com.project2025.digital_library_platform.domain.book.Book;
import com.project2025.digital_library_platform.domain.loan.Loan;
import com.project2025.digital_library_platform.domain.loan.Dtos.LoanCreateDTO;
import com.project2025.digital_library_platform.domain.loan.Dtos.LoanResponseDTO;
import com.project2025.digital_library_platform.domain.user.User;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.repositories.BookRepository;
import com.project2025.digital_library_platform.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanConverter {


    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanConverter(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    public LoanResponseDTO toDto(Loan loan) {
        return new LoanResponseDTO(
                loan.getId(),
                loan.getBook().getTitle(),
                loan.getUser().getUsername(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.isReturned()
        );
    }

    public Loan toEntity(LoanCreateDTO dto) {
        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(() -> new BusinessException("Livro não encontrado", ErrorCode.BOOK_NOT_FOUND));

        User user = userRepository.findById(dto.bookId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));


        return Loan.builder()
                .book(book)
                .user(user)
                .loanDate(LocalDateTime.now())
                .returnDate(LocalDateTime.now().minusDays(7))
                .returned(false)
                .build();
    }

    public List<LoanResponseDTO> converterList(List<Loan> loans) {
        return loans.stream().map(this::toDto).collect(Collectors.toList());
    }

}
