package ru.undframe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.undframe.mode.Role;

public interface RoleDao extends JpaRepository<Role,Long> {
}
