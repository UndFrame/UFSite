package ru.undframe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.undframe.mode.Token;
import ru.undframe.mode.User;

@Component
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByToken(Token token);

    void removeById(long ld);
}
