package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDaoLayer;
import ru.kata.spring.boot_security.demo.dao.UserDaoLayer;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;


/*
UserDetails предоставляет необходимую информацию для построения объекта Authentication
Authentication представляет пользователя (Principal) с точки зрения Spring Security.
(Principal) - аутентифицированный пользователь с полной инфо о нем.

habr.com/ru/post/203318/
*/

//методы для работы с контроллерами
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    final private UserDaoLayer userDao;
    private RoleDaoLayer roleDao;

    //из зависимости подтягиваем методы
    @Autowired
    public UserServiceImpl(UserDaoLayer userDao, RoleDaoLayer roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    //реализация интерфейса UserDetails путем переопределения единственного метода
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //вызываем метод репозитория
        User user = userDao.findByName(username);
        if(user==null)
            throw new UsernameNotFoundException("No one user was found.");
        //если user найден - возвращаем юзера
        return user;
    }


    @Override
    @Transactional
    public User saveUser(User user) {
        userDao.save(user); //метод CRUD-репозитория
        return user;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDao.findAll(); //метод CRUD-репозитория
    }

    @Override
    @Transactional
    public User getUser(Long id) {
        return userDao.getById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }


    @Override
    @Transactional
    public void updateUser(Long id, User user) { userDao.save(user); }


        //поиск пользователя по имени в БД
    @Override
    @Transactional
    public User getByUserName(String username) throws UsernameNotFoundException{
        return userDao.findByName(username); //метод UserDaoLayer
    }
}


