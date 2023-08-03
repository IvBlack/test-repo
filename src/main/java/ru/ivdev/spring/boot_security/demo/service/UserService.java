package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    public User saveUser(User newUser);

    public List<User> getAllUsers();

    public User getUser(Long id);
    public User getByUserName(String name);

    public void deleteById(Long id);
    public void updateUser(Long id, User user);
}
