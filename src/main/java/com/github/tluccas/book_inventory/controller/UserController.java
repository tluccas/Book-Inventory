package com.github.tluccas.book_inventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.tluccas.book_inventory.database.model.User;
import com.github.tluccas.book_inventory.dto.User.AllUsersRes;
import com.github.tluccas.book_inventory.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<AllUsersRes> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.create(user);
        
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable String id) {
        userService.delete(id);
    }
    
    
    
}
