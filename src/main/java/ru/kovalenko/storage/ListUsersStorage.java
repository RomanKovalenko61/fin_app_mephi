package ru.kovalenko.storage;

import ru.kovalenko.model.User;
import ru.kovalenko.utils.GsonLoader;

import java.io.IOException;
import java.util.List;

public class ListUsersStorage implements StorageUser {
    private List<User> users;

    {
        try {
            users = GsonLoader.loadUsers();
        } catch (IOException e) {
            System.err.println("Не удалось загрузить список users");
        }
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public User get(String login, String password) {
        User user = null;
        for (User u : users) {
            if (login.equals(u.getLogin()) && password.equals(u.getPassword())) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String id) {
    }

    @Override
    public boolean isExistsLogin(String login) {
        boolean isExist = false;
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public List<User> getUsers() {
        return users;
    }
}
