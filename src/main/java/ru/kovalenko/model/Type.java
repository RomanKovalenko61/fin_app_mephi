package ru.kovalenko.model;

public enum Type {
    INCOME("Доход"),
    EXPENSE("Расход");

    private final String description;

    Type(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
