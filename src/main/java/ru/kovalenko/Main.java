package ru.kovalenko;

import ru.kovalenko.model.Category;
import ru.kovalenko.model.Operation;
import ru.kovalenko.model.Type;
import ru.kovalenko.model.User;
import ru.kovalenko.storage.*;
import ru.kovalenko.utils.GsonLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Main {

    private static final StorageUser USERS_STORAGE = new ListUsersStorage();

    private static final StorageCategory CATEGORIES_STORAGE = new MapStorageCategory();

    private static final StorageOperations OPERATIONS_STORAGE = new MapStorageOperations();

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
                    if (!USERS_STORAGE.isExistsLogin(login)) {
                        System.err.println(login + " - Ошибка логин не найден");
                        break;
                    }
                    System.out.println("Введите пароль");
                    password = reader.readLine();
                    user = USERS_STORAGE.get(login, password);
                    if (!Objects.isNull(user) && user.getUuid() != null) {
                        System.out.println("Вы успешно вошли в систему");
                    } else {
                        System.err.println("Ошибка авторизации. Неправильная пара логин/пароль");
                    }
                    workOperation(user.getUuid(), reader);
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
                    workOperation(user.getUuid(), reader);
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

    private static void workOperation(UUID uuid, BufferedReader reader) throws IOException {
        List<Category> categories = getUserCategory(uuid);
        List<Operation> operations = getUserOperation(uuid);

        while (true) {
            System.out.println("Введите команду для работы");
            System.out.println(" get | create category | create operation | list categories | list operations | | exit");
            String params[] = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда");
                continue;
            }
            switch (params[0]) {
                case "create":
                    String action = params[1];
                    if (action == null) {
                        System.err.println("Ошибка выполнения команды");
                    }
                    switch (action) {
                        case "category":
                            System.out.println("create category");
                            System.out.println("Введите имя категории");
                            String catName = reader.readLine();
                            System.out.println("Введите тип категории: income - доход expense - расход");
                            Type catType = Type.valueOf(reader.readLine().toUpperCase());
                            System.out.println("Введите лимит средств для категории");
                            Integer limit = Integer.parseInt(reader.readLine());
                            Category newCat = new Category(limit, catName, catType);
                            categories.add(newCat);
                            System.out.println("Категория успешно добавлена");
                            break;
                        case "operation":
                            System.out.println("create operation");
                            System.out.println("Введите сумму операции");
                            Integer sumOp = Integer.parseInt(reader.readLine());
                            System.out.println("Выберите категорию. Введите соответствующую ей цифру");
                            printCategories(categories);
                            Integer catNumber = Integer.parseInt(reader.readLine());
                            //TODO: default category
                            Operation op = new Operation(sumOp, chooseCategory(catNumber, categories));
                            operations.add(op);
                            System.out.println("Операция добавлена");
                            break;
                        default:
                            System.err.println("wrong command");
                            break;
                    }
                    break;
                case "list":
                    String param = params[1];
                    if (param == null) {
                        System.err.println("Ошибка выполнения команды");
                    }
                    switch (param) {
                        case "categories":
                            System.out.println("list categories");
                            categories.forEach(System.out::println);
                            break;
                        case "operations":
                            System.out.println("list operations");
                            operations.forEach(System.out::println);
                            break;
                        default:
                            System.err.println("wrong command");
                            break;
                    }
                    break;
                case "exit":
                    Map<UUID, List<Category>> categoriesMap = CATEGORIES_STORAGE.getStorage();
                    categoriesMap.put(uuid, categories);
                    Map<UUID, List<Operation>> operationsMap = OPERATIONS_STORAGE.getStorage();
                    operationsMap.put(uuid, operations);
                    GsonLoader.saveCategories(categoriesMap);
                    GsonLoader.saveOperations(operationsMap);
                    return;
                default:
                    System.out.println("Неверная команда");
                    break;
            }
        }
    }

    private static Category chooseCategory(Integer catNumber, List<Category> categories) {
        for (int i = 0; i < categories.size(); i++) {
            if (catNumber - 1 == i) {
                return categories.get(i);
            }
        }
        return null;
    }

    private static void printCategories(List<Category> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + " " + list.get(i).getName() + " ");
        }
    }

    private static List<Operation> getUserOperation(UUID uuid) {
        return OPERATIONS_STORAGE.getListById(uuid);
    }

    private static List<Category> getUserCategory(UUID uuid) {
        return CATEGORIES_STORAGE.getListById(uuid);
    }
}