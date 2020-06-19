package ru.undframe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.undframe.dao.RoleDao;
import ru.undframe.dao.TokenDao;
import ru.undframe.dao.UserDao;
import ru.undframe.mode.Role;
import ru.undframe.mode.Token;
import ru.undframe.mode.User;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String name) {
        return userDao.findByUsername(name.toLowerCase());
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email.toLowerCase());
    }

    @Override
    public boolean createUser(User user) {
        User byEmail = findByEmail(user.getEmail());
        User byUsername = findByUsername(user.getUsername());
        if (byEmail != null || byUsername != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(1L));
        user.setRoles(roles);
        Token token = new Token();
        user.setToken(token);
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setDate(new Date());
        user.setBan(false);
        user.setEnabled(false);
        save(user);
        tokenDao.save(token);
        mailService.sendRegisterToken(user);
        return true;
    }

    @Override
    public boolean activateUser(String tokenId) {
        Token token = tokenDao.findByToken(tokenId);
        User user = userDao.findByToken(token);
        if (user == null) {
            return false;
        }
        user.getToken().setUser(null);
        user.setToken(null);
        user.setEnabled(true);
        save(user);
        tokenDao.deleteById(token.getId());
        return true;
    }

}
