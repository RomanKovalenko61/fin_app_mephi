package ru.kovalenko.model;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String login;
    private String password;
    private String name;

    public User() {
        this.uuid = UUID.randomUUID();
    }

    public User(String login, String password) {
        this();
        this.login = login;
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
