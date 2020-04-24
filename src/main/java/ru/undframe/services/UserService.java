package ru.undframe.services;

import ru.undframe.mode.User;

public interface UserService {

    void save(User user);
    User findByUsername(String name);

}
