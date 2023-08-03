package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.EntityGraph;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

//слой логики для поиска юзера в БД через методы JPA-репозитория
@Repository
public interface UserDaoLayer extends JpaRepository<User, Long> {

    //This will load the 'roles' property of the Role entity eagerly
    @EntityGraph(value = "User.roles")
    User findByName(String username);
}
