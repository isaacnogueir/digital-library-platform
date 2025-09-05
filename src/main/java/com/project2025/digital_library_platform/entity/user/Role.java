package com.project2025.digital_library_platform.entity.user;

public enum Role {
    ADMIN("Administrador"),
    LIBRARIAN("Bibliotecário"),
    USER("Usuário");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

