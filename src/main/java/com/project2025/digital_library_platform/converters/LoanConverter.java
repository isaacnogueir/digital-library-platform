package com.project2025.digital_library_platform.converters;

import com.project2025.digital_library_platform.entity.book.Book;
import com.project2025.digital_library_platform.DTOs.loanDtos.LoanCreateDTO;
import com.project2025.digital_library_platform.DTOs.loanDtos.LoanResponseDTO;
import com.project2025.digital_library_platform.entity.Loan;
import com.project2025.digital_library_platform.entity.user.User;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import com.project2025.digital_library_platform.repositories.BookRepository;
import com.project2025.digital_library_platform.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado", ErrorCode.USER_NOT_FOUND));

        return Loan.builder()
                .book(book)
                .user(user)
                .loanDate(LocalDateTime.now())
                .returnDate(LocalDateTime.now().minusDays(7))
                .returned(false)
                .build();
    }
}
