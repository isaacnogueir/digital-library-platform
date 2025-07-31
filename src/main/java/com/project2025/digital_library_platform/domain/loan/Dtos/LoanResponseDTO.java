package com.project2025.digital_library_platform.domain.loan.Dtos;

import java.time.LocalDateTime;

import lombok.*;

/**
 * DTO para resposta contendo informações completas de um empréstimo.
 * resposta
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDTO {

    private Long id;
    private String bookTitle;
    private String userName;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private boolean returned;
}