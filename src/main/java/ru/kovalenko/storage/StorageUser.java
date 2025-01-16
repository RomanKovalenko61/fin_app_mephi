package ru.kovalenko.storage;

import ru.kovalenko.model.User;

public interface StorageUser {

    void save(User user);

    User get(String login, String password);

    void update(User user);

    void delete(String id);

    boolean isExistsLogin(String login);
}
