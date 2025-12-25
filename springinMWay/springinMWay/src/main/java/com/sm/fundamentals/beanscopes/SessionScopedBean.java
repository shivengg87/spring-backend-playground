package com.sm.fundamentals.beanscopes;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

// Marks this class as a Spring-managed component
@Component

/*
 * SESSION scope:
 * - One bean instance per HTTP session (per logged-in user)
 * - Bean is created when the session starts
 * - Same instance reused across multiple requests of the same session
 * - Bean is destroyed when the session expires or is invalidated
 *
 * proxyMode is REQUIRED because this bean is injected
 * into singleton-scoped components (e.g., controllers).
 */
@Scope(
        value = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
public class SessionScopedBean {
    /*
     * Session-specific state.
     * This list is shared across all requests
     * belonging to the same HTTP session.
     */
    private List<Item> items = new ArrayList<>();

    // Adds an item to the user's session-scoped shopping cart
    public void addItem(Item item) {
        items.add(item);
    }

    // Returns all items currently stored in the session
    public List<Item> getItems() {
        return items;
    }
}

class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}