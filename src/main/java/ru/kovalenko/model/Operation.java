package ru.kovalenko.model;

import java.util.UUID;

public class Operation {
    UUID uuid;
    Integer sum;
    UUID categoryId;
    String description;

    Type type;

    public Operation(Integer sum, Type type) {
        uuid = UUID.randomUUID();
        this.sum = sum;
        this.type = type;
    }

    public void setCategory(Category category) {
        this.categoryId = category.getUuid();
    }

    public void setNullCategory() {
        this.categoryId = null;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Type getType() {
        return type;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getSum() {
        return sum;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "uuid=" + uuid +
                ", sum=" + sum +
                ", category=" + categoryId +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
