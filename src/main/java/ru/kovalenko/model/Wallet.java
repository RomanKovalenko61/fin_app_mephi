package ru.kovalenko.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wallet {
    UUID uuid;
    List<Category> categories = new ArrayList<>();
    List<Operation> operations = new ArrayList<>();

    public Wallet(UUID uuid) {
        this.uuid = uuid;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addOperations(Operation operation) {
        operations.add(operation);
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
