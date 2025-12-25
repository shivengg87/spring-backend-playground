package com.sm.fundamentals.beanscopes;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class SessionScopedController {
    /*
     * Injected as a proxy.
     * Actual ShoppingCart instance is resolved
     * based on the current HTTP session.
     */
    private final SessionScopedBean shoppingCart;

    // Constructor injection (recommended)
    public SessionScopedController(SessionScopedBean shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /*
     * Adds an item to the session-scoped shopping cart.
     * Same session → same ShoppingCart instance.
     */
    @PostMapping("/add/{itemName}")
    public String addItem(@PathVariable String itemName) {
        shoppingCart.addItem(new Item(itemName));
        return "Item added: " + itemName;
    }

    /*
     * Retrieves all items stored in the current session's cart.
     */
    @GetMapping("/items")
    public List<Item> getItems() {
        return shoppingCart.getItems();
    }
}
/***
 * curl -X POST -c cookies.txt -b cookies.txt http://localhost:8080/cart/add/banana
 * curl -X POST -c cookies.txt -b cookies.txt http://localhost:8089/cart/add/apple
 *  -c cookies.txt → save session cookie
 *  -b cookies.txt → send same cookie back (same session)
 * curl -X GET -b cookies.txt http://localhost:8089/cart/items
 */