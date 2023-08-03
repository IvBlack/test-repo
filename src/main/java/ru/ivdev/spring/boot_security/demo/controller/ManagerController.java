package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

/*
UserDetails предоставляет необходимую информацию для построения объекта Authentication
Authentication представляет пользователя (Principal) с точки зрения Spring Security.
(Principal) - аутентифицированный пользователь с полной инфо о нем.

habr.com/ru/post/203318/
*/

//контроллер администрирования пользователей
@Controller
@RequestMapping("/admin")
public class ManagerController {
    private final UserService userService;

    public ManagerController(UserService userService) {
        this.userService = userService;
    }

    //страница авторизованного юзера с правами доступа
    @GetMapping
    public String showMainPage(Model mod, Authentication auth) {
        mod.addAttribute("sessionUser", userService.getByUserName(auth.getName()));
        return "admin";
    }
}

