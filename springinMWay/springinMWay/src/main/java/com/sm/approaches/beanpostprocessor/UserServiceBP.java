package com.sm.approaches.beanpostprocessor;


import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Service class demonstrating BeanPostProcessor effects
 * This bean will be processed by multiple BeanPostProcessors
 */
@Service
@Auditable("User Management Service")
@LogExecution(logArgs = true, logResult = true)
public class UserServiceBP {
    private String serviceName = "UserService";
    @SecureField
    private String apiKey = "plain-text-api-key";
    @SecureField
    private String password = "admin123";
    private String publicData = "This is public";
    // Constructor
    public UserServiceBP() {
        System.out.println("   [LIFECYCLE] UserService - Constructor called");
    }
    // @PostConstruct - Called BETWEEN pre and post initialization
    @PostConstruct
    public void init() {
        System.out.println("   [LIFECYCLE] UserService - @PostConstruct called");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("   [LIFECYCLE] UserService - @PreDestroy called");
    }

    @PerformanceMonitored
    public String getUserById(Long id) {
        // Simulate some processing
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "User-" + id;
    }
    public void updateUser(Long id, String name) {
        System.out.println("Updating user " + id + " with name: " + name);
    }
    @PerformanceMonitored
    public String performComplexOperation() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Complex operation completed";
    }
    // Getters and Setters
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPublicData() { return publicData; }
}
