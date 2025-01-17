package ru.kovalenko.storage;

import ru.kovalenko.model.Operation;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StorageOperations {
    void add(UUID uuid, List<Operation> operations);

    List<Operation> getListById(UUID uuid);

    Map<UUID, List<Operation>> getStorage();
}
