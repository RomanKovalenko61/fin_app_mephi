package ru.kovalenko.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.kovalenko.model.Category;
import ru.kovalenko.model.Operation;
import ru.kovalenko.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class GsonLoader {
    private static final Gson GSON = new Gson();

    private static final File STORAGE_USERS = new File("config\\users.json");

    private static final File STORAGE_CATEGORIES = new File("config\\categories.json");

    private static final File STORAGE_OPERATIONS = new File("config\\operations.json");

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

    public static void saveCategories(Map<UUID, List<Category>> categories) throws IOException {
        Type type = new TypeToken<Map<UUID, List<Category>>>() {
        }.getType();
        String json = GSON.toJson(categories, type);

        writer(json, STORAGE_CATEGORIES);
    }

    public static Map<UUID, List<Category>> loadCategories() throws IOException {
        Type type = new TypeToken<Map<UUID, List<Category>>>() {
        }.getType();

        return readerMap(STORAGE_CATEGORIES, type);
    }

    public static void saveOperations(Map<UUID, List<Operation>> operations) throws IOException {
        Type type = new TypeToken<Map<UUID, List<Operation>>>() {
        }.getType();
        String json = GSON.toJson(operations, type);

        writer(json, STORAGE_OPERATIONS);
    }

    public static Map<UUID, List<Operation>> loadOperations() throws IOException {
        Type type = new TypeToken<Map<UUID, List<Operation>>>() {
        }.getType();

        return readerMap(STORAGE_OPERATIONS, type);
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
