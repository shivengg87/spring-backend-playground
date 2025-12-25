package com.sm.approaches.componentscanning.controller;

import com.sm.approaches.componentscanning.model.User;
import com.sm.approaches.componentscanning.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        System.out.println("UserController bean created");
    }

    @PostMapping("/{id}/{name}")
    public User createUser(@PathVariable Long id,
                           @PathVariable String name) {
        return userService.createUser(id, name);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}