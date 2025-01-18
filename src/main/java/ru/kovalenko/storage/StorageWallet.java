package ru.kovalenko.storage;

import ru.kovalenko.model.Wallet;

import java.util.Map;
import java.util.UUID;

public interface StorageWallet {
    void save(Wallet wallet);

    Wallet get(UUID uuid);

    Map<UUID, Wallet> getStorage();
}
