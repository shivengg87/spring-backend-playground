package com.sm.approaches.componentscanning.service;

import com.sm.approaches.componentscanning.model.User;
import com.sm.approaches.componentscanning.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Spring sees only ONE constructor → auto-injects dependency
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("UserService bean created");
    }

    public User createUser(Long id, String name) {
        User user = new User(id, name);
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id);
    }
}