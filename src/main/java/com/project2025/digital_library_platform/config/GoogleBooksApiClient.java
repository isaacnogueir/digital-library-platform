package com.project2025.digital_library_platform.client;

import com.project2025.digital_library_platform.domain.book.googleBooks.GoogleBookSelectionDTO;
import com.project2025.digital_library_platform.domain.book.googleBooks.GoogleBooksResponseDTO;
import com.project2025.digital_library_platform.exception.BusinessException;
import com.project2025.digital_library_platform.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleBooksApiClient {

    private final WebClient googleBooksWebClient;

    public List<GoogleBookSelectionDTO> searchBooks(String query) {
        try {
            GoogleBooksResponseDTO response = googleBooksWebClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("q", query)
                            .queryParam("maxResults", 5)
                            .queryParam("printType", "books") // ← CORRIGIDO: era "PrintType"
                            .build())
                    .retrieve()
                    .bodyToMono(GoogleBooksResponseDTO.class)
                    .block();

            return Optional.ofNullable(response)
                    .map(GoogleBooksResponseDTO::getItems)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(this::mapToSelectionDTO)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Erro ao buscar livros no Google Books: {}", e.getMessage());
            throw new BusinessException("Erro na busca: " + e.getMessage(), ErrorCode.BOOK_NOT_FOUND);
        }
    }

    // NOVO - buscar por ID específico
    public GoogleBookSelectionDTO findById(String googleBookId) {
        try {
            GoogleBooksResponseDTO.GoogleBookItemDto item = googleBooksWebClient
                    .get()
                    .uri("/{id}", googleBookId)
                    .retrieve()
                    .bodyToMono(GoogleBooksResponseDTO.GoogleBookItemDto.class)
                    .block();

            return item != null ? mapToSelectionDTO(item) : null;

        } catch (Exception e) {
            log.error("Erro ao buscar livro por ID no Google Books: {}", e.getMessage());
            throw new BusinessException("Erro ao buscar livro: " + e.getMessage(), ErrorCode.BOOK_NOT_FOUND);
        }
    }

    // ==== MÉTODOS AUXILIARES ====
    private GoogleBookSelectionDTO mapToSelectionDTO(GoogleBooksResponseDTO.GoogleBookItemDto item) {
        GoogleBooksResponseDTO.VolumeInfoDto volumeInfo = item.getVolumeInfo();

        if (volumeInfo == null) { // ← CORRIGIDO: verificar volumeInfo, não item.getVolumeInfo()
            return null;
        }

        String isbn10 = extractIsbn(volumeInfo, "ISBN_10");
        String isbn13 = extractIsbn(volumeInfo, "ISBN_13");

        String thumbnailUrl = Optional.ofNullable(volumeInfo.getImageLinks())
                .map(GoogleBooksResponseDTO.ImageLinkDto::getThumbnail)
                .orElse(null);

        String authors = Optional.ofNullable(volumeInfo.getAuthors())
                .filter(list -> !list.isEmpty())
                .map(list -> String.join(", ", list))
                .orElse(null);

        return new GoogleBookSelectionDTO(
                item.getId(),
                volumeInfo.getTitle(),
                authors,
                volumeInfo.getPublisher(),
                volumeInfo.getPublishedDate(),
                volumeInfo.getDescription(),
                thumbnailUrl,
                isbn10,
                isbn13,
                volumeInfo.getPageCount()
        );
    }

    private String extractIsbn(GoogleBooksResponseDTO.VolumeInfoDto volumeInfo, String isbnType) {
        return Optional.ofNullable(volumeInfo.getIndustryIdentifiers())
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(identifier -> isbnType.equals(identifier.getType()))
                .map(GoogleBooksResponseDTO.IndustryIdentifierDto::getIdentifier)
                .findFirst()
                .orElse(null);
    }
}