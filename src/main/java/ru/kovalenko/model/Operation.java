package ru.kovalenko.model;

import java.util.UUID;

public class Operation {
    UUID uuid;
    Integer sum;
    Category category;
    String description;

    Type type;

    public Operation(Integer sum, Type type) {
        uuid = UUID.randomUUID();
        this.sum = sum;
        this.type = type;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public Integer getSum() {
        return sum;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "uuid=" + uuid +
                ", sum=" + sum +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
