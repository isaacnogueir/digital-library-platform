package com.project2025.digital_library_platform.domain.book.googleBooks;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveFromGoogleDto {

    private String googleBookId;
    private String query;
    private Boolean overrideifExists;

}
