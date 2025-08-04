package com.project2025.digital_library_platform.converters;

import com.project2025.digital_library_platform.domain.book.Status;
import com.project2025.digital_library_platform.domain.book.dtos.BookCreateDTO;
import com.project2025.digital_library_platform.domain.book.googleBooks.GoogleBookSelectionDTO;
import com.project2025.digital_library_platform.domain.book.googleBooks.GoogleBooksResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GoogleBookConverter {

    //Um item de GooleResponseDto PARA GooleBookSelection
    public GoogleBookSelectionDTO toSelectionDto(GoogleBooksResponseDTO.GoogleBookItemDto item) {
        if (item == null || item.getVolumeInfo() == null) {
            return null;
        }

        GoogleBooksResponseDTO.VolumeInfoDto volume = item.getVolumeInfo();

        String isbn10 = extractIsbn(volume, "ISBN_10");
        String isbn13 = extractIsbn(volume, "ISBN_13");

        String authors = Optional.ofNullable(volume.getAuthors())
                .filter(list -> !list.isEmpty())
                .map(list -> String.join(", ", list))
                .orElse(null);

        return new GoogleBookSelectionDTO(
                item.getId(),
                volume.getTitle(),
                authors,
                volume.getPublisher(),
                volume.getPublishedDate(),
                volume.getDescription(),
                isbn10,
                isbn13,
                volume.getPageCount()
        );

    }

    //GoogleResponse Dto par Lista
    public List<GoogleBookSelectionDTO> toSelectionDtoList(GoogleBooksResponseDTO response) {
        if (response == null || response.getItems() == null) {
            return Collections.emptyList();
        }

        return response.getItems().stream()
                .map(this::toSelectionDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    //GoogleBookSelectionDTO para BookCreateDTO
    public BookCreateDTO toCreateDto(GoogleBookSelectionDTO googleBook) {
        return new BookCreateDTO(
                googleBook.title(),
                googleBook.authors(),
                googleBook.publisher(),
                googleBook.publishedDate(),
                googleBook.isbn10(),
                googleBook.isbn13(),
                googleBook.description(),
                googleBook.pageCount(),
                googleBook.googleBooksId(),
                Status.AVAILABLE
        );
    }

    private String extractIsbn(GoogleBooksResponseDTO.VolumeInfoDto volumeInfoDto, String isbnType) {
        return Optional.ofNullable(volumeInfoDto.getIndustryIdentifiers())
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull)
                .filter(identifier -> isbnType.equals(identifier.getType()))
                .map(GoogleBooksResponseDTO.IndustryIdentifierDto::getIdentifier)
                .findFirst()
                .orElse(null);
    }


}
