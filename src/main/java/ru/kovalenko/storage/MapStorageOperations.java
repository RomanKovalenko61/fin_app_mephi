package ru.kovalenko.storage;

import ru.kovalenko.model.Operation;
import ru.kovalenko.utils.GsonLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MapStorageOperations implements StorageOperations {
    Map<UUID, List<Operation>> storage;

    {
        try {
            storage = GsonLoader.loadOperations();
        } catch (IOException e) {
            System.err.println("Не удалось загрузить Map operations");
        }
    }

    @Override
    public void add(UUID uuid, List<Operation> operation) {
        storage.put(uuid, operation);
    }

    @Override
    public List<Operation> getListById(UUID uuid) {
        return storage.getOrDefault(uuid, new ArrayList<>());
    }

    @Override
    public Map<UUID, List<Operation>> getStorage() {
        return storage;
    }
}
