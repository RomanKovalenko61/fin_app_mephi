package ru.kovalenko;

import ru.kovalenko.model.*;
import ru.kovalenko.storage.ListUsersStorage;
import ru.kovalenko.storage.MapStorageWallet;
import ru.kovalenko.storage.StorageUser;
import ru.kovalenko.storage.StorageWallet;
import ru.kovalenko.utils.GsonLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final StorageUser USERS_STORAGE = new ListUsersStorage();

    private static final StorageWallet WALLETS_STORAGE = new MapStorageWallet();

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
        Wallet wallet = WALLETS_STORAGE.get(uuid);

        while (true) {
            printStateWallet(wallet);
            System.out.println("Введите команду для работы");
            System.out.println("create category | create operation | list categories | list operations | exit");
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
                            System.out.println("Введите лимит для категории");
                            Integer catLimit = Integer.parseInt(reader.readLine());
                            System.out.println("Выберите тип категории");
                            Type catType = chooseTypes(reader, Type.values());
                            if (Objects.isNull(catType)) {
                                System.err.println("Ошибка при создании операции. Попробуйте еще раз");
                            } else {
                                Category newCat = new Category(catLimit, catName, catType);
                                wallet.addCategory(newCat);
                                System.out.println("Категория успешно добавлена");
                                System.out.println("Info: " + newCat);
                            }
                            break;
                        case "operation":
                            System.out.println("create operation");
                            System.out.println("Введите сумму операции");
                            Integer sumOp = Integer.parseInt(reader.readLine());
                            System.out.println("Выберите тип операции");
                            Type typeOp = chooseTypes(reader, Type.values());
                            if (Objects.isNull(typeOp)) {
                                System.err.println("Ошибка при создании операции. Попробуйте еще раз");
                            } else {
                                Operation newOp = new Operation(sumOp, typeOp);
                                System.out.println("Выберите категорию для операции");
                                Category catOp = chooseCategory(reader, wallet, typeOp);
                                newOp.setCategory(catOp);
                                wallet.addOperations(newOp);
                                System.out.println("Операция добавлена");
                                System.out.println("Info: " + newOp);
                            }
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
                            List<Category> categories = wallet.getCategories();
                            categories.forEach(System.out::println);
                            break;
                        case "operations":
                            System.out.println("list operations");
                            List<Operation> operations = wallet.getOperations();
                            operations.forEach(System.out::println);
                            break;
                        default:
                            System.err.println("wrong command");
                            break;
                    }
                    break;
                case "exit":
                    WALLETS_STORAGE.save(wallet);
                    Map<UUID, Wallet> walletsMap = WALLETS_STORAGE.getStorage();
                    GsonLoader.saveWallets(walletsMap);
                    return;
                default:
                    System.out.println("Неверная команда");
                    break;
            }
        }
    }

    private static void printStateWallet(Wallet wallet) {
        Integer balance = 0;
        Integer generalIncome = 0;
        Integer generalExpense = 0;
        List<Operation> operations = wallet.getOperations();
        Map<Category, Integer> summaryIncome = new HashMap<>();
        Map<Category, Integer> summaryExpense = new HashMap<>();
        for (int i = 0; i < operations.size(); i++) {
            Operation op = operations.get(i);
            if (op.getType().equals(Type.INCOME)) {
                balance += op.getSum();
                generalIncome += op.getSum();
                summaryIncome.merge(op.getCategory(), op.getSum(), Integer::sum);
            } else {
                balance -= op.getSum();
                generalExpense += op.getSum();
                summaryExpense.merge(op.getCategory(), op.getSum(), Integer::sum);
            }
        }
        System.out.println("Balance " + balance);
        printMap(summaryIncome, generalIncome, Type.INCOME);
        printMap(summaryExpense, generalExpense, Type.EXPENSE);
    }

    private static void printMap(Map<Category, Integer> summary, Integer general, Type type) {
        System.out.println((type.equals(Type.INCOME) ? "Общий доход: " : "Общие расходы: ") + general);
        System.out.println(type.equals(Type.INCOME) ? "Доходы по категориям: " : "Бюджет по категориям: ");
        for (Map.Entry<Category, Integer> entry : summary.entrySet()) {
            if (entry.getKey() != null) {
                System.out.print(entry.getKey().getName() + ": " + entry.getValue());
                if (type.equals(Type.EXPENSE)) {
                    System.out.println(" Оставшийся бюджет: " + (entry.getKey().getLimit() - summary.get(entry.getKey())));
                } else {
                    System.out.println("");
                }
            }
        }
    }

    private static Category chooseCategory(BufferedReader reader, Wallet wallet, Type type) throws IOException {
        List<Category> categories = wallet.getCategories().stream().filter(c -> c.getType().equals(type)).toList();
        System.out.println("0 - Без категории");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + " - " + categories.get(i).getName());
        }
        Integer number = Integer.parseInt(reader.readLine());
        if (number > categories.size()) {
            System.err.println("Вы ввели неправильный номер. Значении категории не будет присвоено");
            return null;
        } else if (number - 1 == -1) {
            return null;
        }
        return categories.get(number - 1);
    }

    private static Type chooseTypes(BufferedReader reader, Type[] types) throws IOException {
        for (int i = 0; i < types.length; i++) {
            System.out.println(i + " - " + types[i].getDescription());
        }
        int number = Integer.parseInt(reader.readLine());
        if (number >= types.length) {
            System.err.println("Вы ввели неправильный номер");
            return null;
        }
        return Type.values()[number];
    }
}