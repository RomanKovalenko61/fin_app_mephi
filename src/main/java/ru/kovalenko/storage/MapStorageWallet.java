package ru.kovalenko.storage;

import ru.kovalenko.model.Wallet;
import ru.kovalenko.utils.GsonLoader;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class MapStorageWallet implements StorageWallet {

    Map<UUID, Wallet> storage;

    {
        try {
            storage = GsonLoader.loadWallets();
        } catch (IOException e) {
            System.err.println("Не удалось загрузить Map wallets");
        }
    }

    @Override
    public void save(Wallet wallet) {
        storage.put(wallet.getUuid(), wallet);
    }

    @Override
    public Wallet get(UUID uuid) {
        return storage.getOrDefault(uuid, new Wallet(uuid));
    }

    @Override
    public Map<UUID, Wallet> getStorage() {
        return storage;
    }
}
