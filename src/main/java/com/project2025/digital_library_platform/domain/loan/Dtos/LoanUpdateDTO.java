package com.project2025.digital_library_platform.domain.loan.Dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LoanUpdateDTO(
        @NotNull(message = "ID do emprestimo é obrigadotio")
        Long id, //Qual empréstimo será atualizado
        LocalDateTime returnDate, //Data devolução
        Boolean returned) //Devolução
{
}
