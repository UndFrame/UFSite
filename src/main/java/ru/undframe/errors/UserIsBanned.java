package ru.undframe.errors;

import org.springframework.security.core.AuthenticationException;

public class UserIsBanned extends AuthenticationException {
    public UserIsBanned(String msg) {
        super(msg);
    }
}
