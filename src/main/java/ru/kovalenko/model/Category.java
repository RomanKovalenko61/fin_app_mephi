package ru.kovalenko.model;

import java.util.Objects;
import java.util.UUID;

public class Category {

    UUID uuid;
    Integer limit;
    String name;

    Type type;

    public Category(Integer limit, String name, Type type) {
        uuid = UUID.randomUUID();
        this.limit = limit;
        this.name = name;
        this.type = type;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public Integer getLimit() {
        return limit;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return uuid.equals(category.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Category{" +
                "uuid=" + uuid +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}