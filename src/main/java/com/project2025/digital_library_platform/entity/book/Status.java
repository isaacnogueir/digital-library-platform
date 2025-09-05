package com.project2025.digital_library_platform.entity.book;

public enum Status {

    AVAILABLE("DISPONIVEL"),
    LOANED("EMPRESTADO"),
    UNAVAILABLE("INDISPONIVEL");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
