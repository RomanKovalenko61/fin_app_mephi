package ru.kovalenko.model;

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
    public String toString() {
        return "Category{" +
                "uuid=" + uuid +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}