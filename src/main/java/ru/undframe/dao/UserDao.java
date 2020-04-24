package ru.undframe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.undframe.mode.User;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
