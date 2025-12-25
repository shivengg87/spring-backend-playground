package com.sm.approaches.configurationclass;

public class EmailService implements NotificationService {

    private final String appName;

    // Constructor injection - dependencies provided during bean creation
    public EmailService(String appName) {
        this.appName = appName;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("[" + appName + "] Sending Email: " + message);
    }

    @Override
    public String getServiceType() {
        return "EMAIL";
    }

    // Custom initialization method - called after bean creation
    public void init() {
        System.out.println("EmailService initialized for app: " + appName);
    }

    // Custom destroy method - called before bean destruction
    public void cleanup() {
        System.out.println("EmailService cleanup completed");
    }
}
