package com.project2025.digital_library_platform.controllers;

import com.project2025.digital_library_platform.DTOs.loanDtos.LoanCreateDTO;
import com.project2025.digital_library_platform.DTOs.loanDtos.LoanResponseDTO;
import com.project2025.digital_library_platform.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loan Management", description = "Operações ralacionados ao gerencimaento de empréstimos")
@SecurityRequirement(name = "bearerAuth")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
// ==== OPERAÇÕES DE CRIAÇÃO E ATUALIZAÇÃO ====

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo empréstimo", description = "Registrar um novo empréstimo no sistema")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<LoanResponseDTO> registerLoan(@Valid @RequestBody LoanCreateDTO dto) {
        LoanResponseDTO register = loanService.registerLoan(dto);
        return ResponseEntity.ok(register);
    }

    @PutMapping("/return/{id}")
    @Operation(summary = "Devolver um livro", description = "Devolver um livro emprestado no sistema")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<LoanResponseDTO> returnLoan(@PathVariable Long id) {
        loanService.returnLoan(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancel/{id}")
    @Operation(summary = "Cancelar empréstimo", description = "Cancelar empréstimo de um livro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LoanResponseDTO> cancelLoan(@PathVariable Long id) {
        loanService.cancelLoan(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/actives")
    @Operation(summary = "Listar empréstimos ativos", description = "Listar todos empréstimos em andamento")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<List<LoanResponseDTO>> findActiveLoans() {
        List<LoanResponseDTO> list = loanService.findActiveLoans();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/findOver")
    @Operation(summary = "Listar empréstimos vencidos", description = "Lista empréstimos vencidos (não devolvidos e com data de retorno expirada)")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<List<LoanResponseDTO>> findOverDueLoands() {
        List<LoanResponseDTO> list = loanService.findOverDueLoans();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/LoansByUser/{id}")
    @Operation(summary = "Listar empréstimos do usuário", description = "Listar todoso so empréstimos realizados pelo usuário")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoanResponseDTO>> findLoansByUser(@PathVariable Long id){
        List<LoanResponseDTO> list = loanService.findLoansByUser(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("findByUser/{id}")
    @Operation(summary = "Buscar empréstimos por ID", description = "Buscar empréstimos por id do usuário")
    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    public ResponseEntity<LoanResponseDTO> findLoanByUser(@PathVariable Long id) {
        LoanResponseDTO loan = loanService.findLoanById(id);
        return ResponseEntity.ok(loan);
    }
}