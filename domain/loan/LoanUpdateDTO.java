package com.project2025.auth_2.domain.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LoanUpdateDTO(
        @NotNull(message = "ID do emprestimo é obrigadotio")
        Long id, //Qual empréstimo será atualizado
        LocalDateTime returnDate, //Data devolução
        Boolean returned) //Devolução
{
}
