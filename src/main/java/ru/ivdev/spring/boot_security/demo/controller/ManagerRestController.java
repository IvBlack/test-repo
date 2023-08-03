package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class ManagerRestController {
    private final UserService userService;

    public ManagerRestController(UserService userService) {
        this.userService = userService;
    }

    //all users on main page
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    //returns user by his id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws EntityNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.getUser(id));
        if(user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /*
    //ResponseEntity sets body, status and headers of an HTTP response
    @GetMapping("{user:\\d+}")
    public ResponseEntity getUserById(@PathVariable("user") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
    */

    //create new user
    @PostMapping(path="/add",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<User> addNewUser(@RequestBody User newUser) throws Exception{
        User user = userService.saveUser(newUser);
        if(user==null) {
            throw new Exception();
        } else {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    //delete user by id, returns operation status only
    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) throws IllegalArgumentException {
        try{
            userService.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception ex) {
            throw new IllegalArgumentException();
        }
    }

    //updating current user by id, returns operation status only
    @PutMapping("/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable("id") Long id) throws IllegalArgumentException {
        try{
            userService.updateUser(id, user);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch(Exception ex) {
            throw new IllegalArgumentException();
        }
    }
}
