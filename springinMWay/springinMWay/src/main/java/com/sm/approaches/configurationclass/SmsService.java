package com.sm.approaches.configurationclass;

public class SmsService implements NotificationService {

    private final String appName;

    public SmsService(String appName) {
        this.appName = appName;
    }

    @Override
    public void sendNotification(String message) {
        System.out.println("[" + appName + "] Sending SMS: " + message);
    }

    @Override
    public String getServiceType() {
        return "SMS";
    }
}

