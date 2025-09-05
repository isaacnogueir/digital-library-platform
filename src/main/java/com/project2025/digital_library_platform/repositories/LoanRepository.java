package com.project2025.digital_library_platform.repositories;


import com.project2025.digital_library_platform.entity.book.Book;
import com.project2025.digital_library_platform.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByReturnedFalse();

    boolean existsByBookAndReturnedFalse(Book book);

    List<Loan> findByReturnDateBeforeAndReturnedFalse(LocalDateTime date);

    List<Loan> findAllByUserId(Long userId);

    //List<Loan> findByUserId(Long id);

    //Optional<Loan> findByBookIdAndUserId(Long bookId, Long userId);
}
