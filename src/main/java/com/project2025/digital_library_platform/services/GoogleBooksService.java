package com.project2025.digital_library_platform.services;

import com.project2025.digital_library_platform.converters.GoogleBookConverter;
import com.project2025.digital_library_platform.DTOs.bookDtos.BookCreateDTO;
import com.project2025.digital_library_platform.DTOs.bookDtos.BookResponseDTO;
import com.project2025.digital_library_platform.DTOs.googleBooksDtos.GoogleBookSelectionDTO;
import com.project2025.digital_library_platform.DTOs.googleBooksDtos.GoogleBooksResponseDTO;
import com.project2025.digital_library_platform.DTOs.googleBooksDtos.SaveFromGoogleDto;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleBooksService {


    private final WebClient googleBooksWebClient;
    private final BookService bookService;
    private final GoogleBookConverter googleBookConverter;

    // ==== METODO PRINCIPAIS ====

    public List<GoogleBookSelectionDTO> seacherBooksSync(String query) {
        GoogleBooksResponseDTO response = googleBooksWebClient.get()
                .uri("?q={query}&maxResults=10&printType=books", query)
                .retrieve()
                .bodyToMono(GoogleBooksResponseDTO.class)
                .block();

        return googleBookConverter.toSelectionDtoList(response);

    }

    @Transactional
    public BookResponseDTO saveBookFromGoogle(SaveFromGoogleDto dto) {

        if (dto.getGoogleBookId() == null || dto.getGoogleBookId().trim().isEmpty()) {
            throw new BusinessException("Goole Book ID obrigatório", ErrorCode.INVALID_CREDENTIALS);
        }

        GoogleBookSelectionDTO googleBook = googleBooksWebClient.get()
                .uri("/{id}", dto.getGoogleBookId())
                .retrieve()
                .bodyToMono(GoogleBooksResponseDTO.GoogleBookItemDto.class)
                .map(googleBookConverter::toSelectionDto)
                .block();

        if (googleBook == null) {
            throw new BusinessException("Livro não encontrado no Google Books", ErrorCode.BOOK_NOT_FOUND);
        }
        BookCreateDTO createDto = googleBookConverter.toCreateDto(googleBook);
        return bookService.createBook(createDto);

    }
}