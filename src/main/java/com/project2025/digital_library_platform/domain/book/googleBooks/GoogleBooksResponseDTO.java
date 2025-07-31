package com.project2025.digital_library_platform.domain.book.googleBooks;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBooksResponseDTO {

    private String kind;
    private int totalItems;
    private List<GoogleBookItemDto> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GoogleBookItemDto {
        private String id;
        private VolumeInfoDto volumeInfo;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VolumeInfoDto {
        private String title;
        private List<String> authors;
        private String publisher;
        private String publishedDate;
        private String description;
        private Integer pageCount;
        private List<IndustryIdentifierDto> industryIdentifiers;
        private ImageLinkDto imageLinks;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IndustryIdentifierDto {
        private String type;
        private String identifier;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageLinkDto {
        private String thumbnail;
    }

}
