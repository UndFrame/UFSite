package ru.undframe.services;

import ru.undframe.mode.User;

public interface UserService {

    void save(User user);
    User findByUsername(String name);
    User findByEmail(String name);

    boolean createUser(User user);

    boolean activateUser(String token);
}
