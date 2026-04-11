package com.library.model;

public enum BookCategory {
    FICTION("Fiction"),
    SCIENCE("Science"),
    HISTORY("History"),
    TECHNOLOGY("Technology"),
    BIOGRAPHY("Biography"),
    OTHER("Other");

    private final String displayName;

    BookCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
