package com.project2025.digital_library_platform.DTOs.loanDtos;

import java.time.LocalDateTime;

import lombok.*;

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