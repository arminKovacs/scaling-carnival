package com.codecool.shifterbackend.controller;

import com.codecool.shifterbackend.entity.User;
import com.codecool.shifterbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    private List<User> getAllUsers() {
        return userService.getAll();
    };

    @GetMapping("/{userId}")
    private User getUserById(Long userId) {
        return userService.returnById(userId);
    };

}
