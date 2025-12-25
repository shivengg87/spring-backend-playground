package com.sm.approaches.componentscanning.repo;

import com.sm.approaches.componentscanning.model.User;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    // In-memory store just for demonstration
    private final Map<Long, User> store = new HashMap<>();

    public UserRepository() {
        // Proves bean creation
        System.out.println("UserRepository bean created");
    }

    public User findById(Long id) {
        return store.get(id);
    }

    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }
}