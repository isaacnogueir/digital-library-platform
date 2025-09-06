package com.project2025.digital_library_platform.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomainEventDTO {
    private String action;
    private String entityType;
    private Long entityId;
    private String details;
}
