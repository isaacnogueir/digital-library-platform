package com.project2025.digital_library_platform.controllers;

import com.project2025.digital_library_platform.domain.book.dtos.BookResponseDTO;
import com.project2025.digital_library_platform.domain.book.googleBooks.GoogleBookSelectionDTO;
import com.project2025.digital_library_platform.domain.book.googleBooks.SaveFromGoogleDto;
import com.project2025.digital_library_platform.services.GoogleBooksService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/google-books")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Slf4j
public class GoogleBooksController {

    private final GoogleBooksService googleBooksService;

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GoogleBookSelectionDTO>> searchBooks(@RequestParam String query) {
        return ResponseEntity.ok(googleBooksService.seacherBooksSync(query));
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> saveBook(@RequestBody SaveFromGoogleDto dto) {
        return ResponseEntity.ok(googleBooksService.saveBookFromGoogle(dto));
    }
}
