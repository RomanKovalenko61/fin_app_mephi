package ru.kovalenko.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.kovalenko.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonLoader {
    private static final Gson GSON = new Gson();

    private static final File STORAGE_USERS = new File("config\\users.json");

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

    private static List<User> reader(File file, Type type) throws IOException {
        if (!file.exists()) {
            return new ArrayList<User>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return GSON.fromJson(sb.toString(), type);
        }
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
}
