package ru.kovalenko.storage;

import ru.kovalenko.model.Category;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface StorageCategory {
    void add(UUID uuid, List<Category> list);

    List<Category> getListById(UUID uuid);

    Map<UUID, List<Category>> getStorage();
}
