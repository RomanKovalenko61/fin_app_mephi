package ru.kovalenko.model;

import java.util.*;
import java.util.stream.Collectors;

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

    public void updateOperation(Operation operation) {
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

    public boolean isOperation(UUID uuid) {
        return operations.containsKey(uuid);
    }

    public boolean isCategory(UUID uuid) {
        return categories.containsKey(uuid);
    }

    public Operation getOperationByUUID(UUID uuid) {
        return operations.get(uuid);
    }

    public List<Operation> getOperations() {
        return new ArrayList<>(operations.values());
    }

    public List<Operation> getOperationsByCategoryId(UUID id) {
        return operations.values().stream().filter(op -> Objects.nonNull(op.categoryId) && op.categoryId.equals(id)).collect(Collectors.toList());
    }

    public void deleteOperationByUUID(UUID idOperation) {
        operations.remove(idOperation);
    }

    public void deleteCategoryByUUID(UUID idCategory) {
        categories.remove(idCategory);
    }
}
