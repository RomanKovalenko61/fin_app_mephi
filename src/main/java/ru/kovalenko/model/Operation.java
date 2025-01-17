package ru.kovalenko.model;

public class Operation {
    Integer sum;
    Category category;
    String description;

    public Operation(Integer sum, Category category) {
        this.sum = sum;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "sum=" + sum +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}
