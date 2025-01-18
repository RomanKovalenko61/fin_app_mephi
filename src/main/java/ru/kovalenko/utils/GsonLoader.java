package ru.kovalenko.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.kovalenko.model.User;
import ru.kovalenko.model.Wallet;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class GsonLoader {
    private static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();

    private static final File STORAGE_USERS = new File("config\\users.json");

    private static final File STORAGE_WALLETS = new File("config\\wallets.json");

    public static void saveUsers(List<User> users) throws IOException {
        Type type = new TypeToken<List<User>>() {
        }.getType();
        String json = GSON.toJson(users, type);

        writer(json, STORAGE_USERS);
    }

    public static List<User> loadUsers() throws IOException {
        Type type = new TypeToken<List<User>>() {
        }.getType();

        return reader(STORAGE_USERS, type);
    }

    public static void saveWallets(Map<UUID, Wallet> wallets) throws IOException {
        Type type = new TypeToken<Map<UUID, Wallet>>() {
        }.getType();
        String json = GSON.toJson(wallets, type);

        writer(json, STORAGE_WALLETS);
    }

    public static Map<UUID, Wallet> loadWallets() throws IOException {
        Type type = new TypeToken<Map<UUID, Wallet>>() {
        }.getType();

        return readerMap(STORAGE_WALLETS, type);
    }

    private static List<User> reader(File file, Type type) throws IOException {
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return GSON.fromJson(readerString(file), type);
    }

    private static <K, V> Map<K, V> readerMap(File file, Type type) throws IOException {
        if (!file.exists()) {
            return new HashMap<>();
        }
        return GSON.fromJson(readerString(file), type);
    }

    private static void writer(String json, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
            System.out.println("Успешно записано " + file.getAbsolutePath());
        }
    }

    private static String readerString(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}
