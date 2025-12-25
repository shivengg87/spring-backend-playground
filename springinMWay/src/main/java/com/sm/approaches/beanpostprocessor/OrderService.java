package com.sm.approaches.beanpostprocessor;


import org.springframework.stereotype.Service;

@Service
@LogExecution(logArgs = true, logResult = false)
public class OrderService {

    public OrderService() {
        System.out.println("   [LIFECYCLE] OrderService - Constructor called");
    }

    public String createOrder(String productId, int quantity) {
        return "Order created for " + quantity + " units of " + productId;
    }

    public void cancelOrder(String orderId) {
        System.out.println("Order cancelled: " + orderId);
    }
}
