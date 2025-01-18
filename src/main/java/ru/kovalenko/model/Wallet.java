package ru.kovalenko.model;

import java.util.*;

public class Wallet {
    UUID uuid;
    Map<UUID, Category> categories = new HashMap();
    Map<UUID, Operation> operations = new HashMap<>();

    public Wallet(UUID uuid) {
        this.uuid = uuid;
    }

    public void addCategory(Category category) {
        categories.put(category.getUuid(), category);
    }

    public void addOperations(Operation operation) {
        operations.put(operation.getUuid(), operation);
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories.values());
    }

    public Map<UUID, Category> getMapCategories() {
        return categories;
    }

    public List<Operation> getOperations() {
        return new ArrayList<>(operations.values());
    }
}
