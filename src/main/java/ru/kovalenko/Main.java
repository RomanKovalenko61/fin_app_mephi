package ru.kovalenko;

import ru.kovalenko.model.User;
import ru.kovalenko.storage.ListUsersStorage;
import ru.kovalenko.storage.StorageUser;
import ru.kovalenko.utils.GsonLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final StorageUser USERS_STORAGE = new ListUsersStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Введите команду");
            System.out.println("login | create | exit");
            String params[] = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length != 1) {
                System.out.println("Неверная команда");
                continue;
            }
            String login = null;
            String password = null;
            User user = null;
            switch (params[0]) {
                case "login":
                    System.out.println("Введите логин");
                    login = reader.readLine();
                    System.out.println("Введите пароль");
                    password = reader.readLine();
                    user = USERS_STORAGE.get(login, password);
                    if (!Objects.isNull(user) && user.getUuid() != null) {
                        System.out.println("Вы успешно вошли в систему");
                    } else {
                        System.err.println("Ошибка авторизации. Неправильная пара логин/пароль");
                    }
                    break;
                case "create":
                    System.out.println("Введите логин");
                    login = reader.readLine();
                    if (USERS_STORAGE.isExistsLogin(login)) {
                        System.err.println(login + " - Такой логин уже используется");
                        break;
                    }
                    System.out.println("Введите пароль");
                    password = reader.readLine();
                    user = new User(login, password);
                    USERS_STORAGE.save(user);
                    System.out.println("Пользователь успешно создан");
                    break;
                case "exit":
                    System.out.println("Выход из программы");
                    List<User> users = ((ListUsersStorage) USERS_STORAGE).getUsers();
                    GsonLoader.saveUsers(users);
                    return;
                default:
                    System.out.println("Неверная команда");
                    break;
            }
        }
    }
}