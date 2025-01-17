package ru.kovalenko.model;

import java.math.BigInteger;

public class Category {
    Integer limit;
    String name;
    Type type;

    public Category(Integer limit, String name, Type type) {
        this.limit = limit;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "limit=" + limit +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}