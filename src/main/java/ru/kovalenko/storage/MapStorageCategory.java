package ru.kovalenko.storage;

import ru.kovalenko.model.Category;
import ru.kovalenko.utils.GsonLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MapStorageCategory implements StorageCategory {
    Map<UUID, List<Category>> storage;

    {
        try {
            storage = GsonLoader.loadCategories();
        } catch (IOException e) {
            System.err.println("Не удалось загрузить Map categories");
        }
    }

    @Override
    public void add(UUID uuid, List<Category> categories) {
        storage.put(uuid, categories);
    }

    @Override
    public List<Category> getListById(UUID uuid) {
        return storage.getOrDefault(uuid, new ArrayList<>());
    }

    @Override
    public Map<UUID, List<Category>> getStorage() {
        return storage;
    }
}
