package com.sm;
import com.sm.approaches.beanpostprocessor.OrderService;
import com.sm.approaches.beanpostprocessor.UserServiceBP;
import com.sm.approaches.configurationclass.ApiClient;
import com.sm.approaches.configurationclass.AppProperties;
import com.sm.approaches.configurationclass.EmailService;
import com.sm.approaches.configurationclass.NotificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringinMWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringinMWayApplication.class, args);
    }

    /**
     * CommandLineRunner - Executes after application context is loaded
     * Perfect for testing and demonstrating bean behavior
     */
    @Bean
    public CommandLineRunner demo(
            ApplicationContext context,
            AppProperties properties,
            EmailService emailService,
            NotificationService primaryNotificationService,
            ApiClient apiClientWithProperties,
            String applicationInfo) {
        return args -> {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("SPRING BOOT @CONFIGURATION DEMONSTRATION");
            System.out.println("=".repeat(70) + "\n");
            // 1. Show loaded properties
            System.out.println("1. CONFIGURATION PROPERTIES:");
            System.out.println("   App Name: " + properties.getName());
            System.out.println("   Version: " + properties.getVersion());
            System.out.println("   Environment: " + properties.getEnvironment());
            System.out.println("   Email Enabled: " + properties.getFeature().isEmailEnabled());
            System.out.println("   SMS Enabled: " + properties.getFeature().isSmsEnabled());

            // 2. Test notification service
            System.out.println("\n2. NOTIFICATION SERVICE:");
            emailService.sendNotification("Hello from Configuration Class!");

            // 3. Test primary bean (auto-injected without qualifier)
            System.out.println("\n3. PRIMARY BEAN:");
            System.out.println("   Primary service type: " +
                    primaryNotificationService.getServiceType());

            // 4. Test API client
            System.out.println("\n4. API CLIENT:");
            System.out.println("   " + apiClientWithProperties.callApi("/users"));

            // 5. Show application info
            System.out.println("\n5. APPLICATION INFO:");
            System.out.println(applicationInfo);

            // 6. Demonstrate singleton vs prototype
            System.out.println("6. SCOPE DEMONSTRATION:");
            NotificationService singleton1 =
                    context.getBean("singletonService", NotificationService.class);
            NotificationService singleton2 =
                    context.getBean("singletonService", NotificationService.class);
            System.out.println("   Singleton same instance? " +
                    (singleton1 == singleton2)); // true

            NotificationService proto1 =
                    context.getBean("prototypeService", NotificationService.class);
            NotificationService proto2 =
                    context.getBean("prototypeService", NotificationService.class);
            System.out.println("   Prototype same instance? " +
                    (proto1 == proto2)); // false

            // 7. List all beans of type NotificationService
            System.out.println("\n7. ALL NOTIFICATIONSERVICE BEANS:");
            String[] beanNames = context.getBeanNamesForType(NotificationService.class);
            for (String beanName : beanNames) {
                System.out.println("   - " + beanName);
            }

            System.out.println("\n" + "=".repeat(70));
            System.out.println("DEMONSTRATION COMPLETE");
            System.out.println("=".repeat(70) + "\n");
        };
    }

    @Bean
    public CommandLineRunner demo2(UserServiceBP userService, OrderService orderService) {
        return args -> {

            System.out.println("\n" + "=".repeat(70));
            System.out.println("BEANPOSTPROCESSOR DEMONSTRATION");
            System.out.println("=".repeat(70));

            System.out.println("\n1. TESTING UserService (Multiple Processors):");
            System.out.println("-".repeat(70));

            // Check encrypted fields
            System.out.println("\nEncrypted Fields:");
            System.out.println("API Key: " + userService.getApiKey());
            System.out.println("Password: " + userService.getPassword());
            System.out.println("Public Data: " + userService.getPublicData());

            // Test audited and monitored methods
            System.out.println("\n\nCalling getUserById (Audited + Monitored):");
            String user = userService.getUserById(123L);

            System.out.println("\n\nCalling performComplexOperation:");
            userService.performComplexOperation();

            System.out.println("\n\n2. TESTING OrderService (Log Execution):");
            System.out.println("-".repeat(70));

            orderService.createOrder("PRODUCT-001", 5);
            orderService.cancelOrder("ORDER-999");

            System.out.println("\n" + "=".repeat(70));
            System.out.println("DEMONSTRATION COMPLETE");
            System.out.println("=".repeat(70) + "\n");
        };
    }
}
