package com.project2025.digital_library_platform.DTOs.googleBooksDtos;

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
    private Boolean overRideIfExists;

}
