package com.sm.approaches.configurationclass;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;

/**
 * Main Configuration Class demonstrating all key @Bean concepts
 *
 * @Configuration - Marks this class as a source of bean definitions
 * Spring will scan this class and register all @Bean methods
 */
@Configuration
public class AppConfig {

    // ========================================================================
    // PROPERTY INJECTION TECHNIQUES
    // ========================================================================

    /**
     * @Value - Injects individual property values from application.properties
     * SpEL (Spring Expression Language) used with ${} syntax
     */
    @Value("${app.name}")
    private String applicationName;

    @Value("${app.version}")
    private String applicationVersion;


    // ========================================================================
    // BASIC BEAN DEFINITION
    // ========================================================================

    /**
     * Simple bean creation with default singleton scope
     * Bean name will be method name: "emailService"
     * Constructor injection of dependencies
     */
    @Bean
    public EmailService emailService() {
        return new EmailService(applicationName);
    }


    // ========================================================================
    // CUSTOM BEAN NAME
    // ========================================================================

    /**
     * Bean with custom name specified
     * Can be referenced as "smsNotificationService" instead of method name
     * Useful when method name doesn't reflect bean purpose clearly
     */
    @Bean(name = "smsNotificationService")
    public SmsService smsService() {
        return new SmsService(applicationName);
    }


    // ========================================================================
    // BEAN WITH INITIALIZATION AND DESTRUCTION CALLBACKS
    // ========================================================================

    /**
     * Bean with lifecycle callbacks
     * initMethod - called after bean construction and dependency injection
     * destroyMethod - called before bean is removed from container
     *
     * Useful for:
     * - Opening/closing connections
     * - Starting/stopping threads
     * - Acquiring/releasing resources
     */
    @Bean(initMethod = "init", destroyMethod = "cleanup")
    public EmailService managedEmailService() {
        return new EmailService(applicationName + " [Managed]");
    }


    // ========================================================================
    // BEAN SCOPE VARIATIONS
    // ========================================================================

    /**
     * SINGLETON scope (DEFAULT)
     * Only ONE instance created per Spring container
     * Same instance returned for all requests
     * Best for stateless beans
     */
    @Bean
    @Scope("singleton")
    public NotificationService singletonService() {
        return new EmailService("Singleton-" + applicationName);
    }

    /**
     * PROTOTYPE scope
     * NEW instance created every time bean is requested
     * Spring doesn't manage complete lifecycle (no destroy callbacks)
     * Best for stateful beans or when fresh instance needed each time
     */
    @Bean
    @Scope("prototype")
    public NotificationService prototypeService() {
        return new EmailService("Prototype-" + applicationName);
    }


    // ========================================================================
    // CONDITIONAL BEAN CREATION
    // ========================================================================

    /**
     * Conditional bean based on property value
     * Bean only created if app.feature.email-enabled=true
     *
     * @ConditionalOnProperty variations:
     * - havingValue: specific value to match
     * - matchIfMissing: behavior when property is absent
     */
    @Bean
    @ConditionalOnProperty(
            name = "app.feature.email-enabled",
            havingValue = "true",
            matchIfMissing = false
    )
    public NotificationService conditionalEmailService() {
        System.out.println("Creating conditionalEmailService (email feature enabled)");
        return new EmailService("Conditional-" + applicationName);
    }

    /**
     * Another conditional bean for SMS
     * Only created when SMS feature is enabled
     */
    @Bean
    @ConditionalOnProperty(
            name = "app.feature.sms-enabled",
            havingValue = "true"
    )
    public NotificationService conditionalSmsService() {
        System.out.println("Creating conditionalSmsService (SMS feature enabled)");
        return new SmsService("Conditional-" + applicationName);
    }


    // ========================================================================
    // PROFILE-SPECIFIC BEANS
    // ========================================================================

    /**
     * Bean only created when "dev" profile is active
     * Activate with: spring.profiles.active=dev
     *
     * Use cases:
     * - Different configurations for dev/test/prod
     * - Mock services in development
     * - Environment-specific integrations
     */
    @Bean
    @Profile("dev")
    public NotificationService devNotificationService() {
        System.out.println("Creating DEV profile notification service");
        return new EmailService("DEV-" + applicationName);
    }

    /**
     * Bean for production profile
     */
    @Bean
    @Profile("prod")
    public NotificationService prodNotificationService() {
        System.out.println("Creating PROD profile notification service");
        return new EmailService("PROD-" + applicationName);
    }


    // ========================================================================
    // DEPENDENCY INJECTION BETWEEN BEANS
    // ========================================================================

    /**
     * Method 1: Direct method call
     * Simple but creates impression of multiple calls
     * Spring intercepts and returns same singleton instance
     */
    @Bean
    public ApiClient apiClientDirectCall() {
        // Looks like creating new EmailService, but Spring returns singleton
        EmailService email = emailService();
        System.out.println("ApiClient using direct method call");
        return new ApiClient(
                "https://direct.api.com",
                5000,
                3
        );
    }

    /**
     * Method 2: Parameter injection (RECOMMENDED)
     * Clear dependency declaration in method signature
     * Spring automatically injects matching bean
     * Better for readability and testing
     */
    @Bean
    public ApiClient apiClientParameterInjection(
            @Value("${app.api.base-url}") String baseUrl,
            @Value("${app.api.timeout}") int timeout,
            @Value("${app.api.retry-count}") int retryCount) {

        System.out.println("ApiClient created with parameter injection");
        return new ApiClient(baseUrl, timeout, retryCount);
    }

    /**
     * Method 3: Injecting ConfigurationProperties object
     * Best for complex configurations with multiple properties
     * Type-safe and maintainable
     */
    @Bean
    public ApiClient apiClientWithProperties(AppProperties appProperties) {
        System.out.println("ApiClient created using AppProperties");
        return new ApiClient(
                appProperties.getApi().getBaseUrl(),
                appProperties.getApi().getTimeout(),
                appProperties.getApi().getRetryCount()
        );
    }


    // ========================================================================
    // QUALIFIER - RESOLVING AMBIGUITY
    // ========================================================================

    /**
     * When multiple beans of same type exist, use @Qualifier
     * Specifies which exact bean to inject by name
     *
     * Without @Qualifier, Spring would throw NoUniqueBeanDefinitionException
     */
    @Bean
    public String primaryNotificationReport(
            @Qualifier("emailService") NotificationService emailService,
            @Qualifier("smsNotificationService") NotificationService smsService) {

        return String.format(
                "Available Services: %s, %s",
                emailService.getServiceType(),
                smsService.getServiceType()
        );
    }


    // ========================================================================
    // PRIMARY BEAN
    // ========================================================================

    /**
     * @Primary - Marks this as the default bean when multiple candidates exist
     * When Spring finds multiple beans of same type and no @Qualifier specified,
     * it will choose the @Primary bean
     *
     * Only ONE bean of a type should be marked as @Primary
     */
    @Bean
    @Primary
    public NotificationService primaryNotificationService() {
        System.out.println("Creating PRIMARY notification service");
        return new EmailService("Primary-" + applicationName);
    }


    // ========================================================================
    // LAZY INITIALIZATION
    // ========================================================================

    /**
     * @Lazy - Bean created only when first requested, not at startup
     *
     * Benefits:
     * - Faster application startup
     * - Saves resources if bean never used
     *
     * Use cases:
     * - Heavy initialization
     * - Optional features
     * - Beans that may not be needed in every execution
     */
    @Bean
    @Lazy
    public NotificationService lazyNotificationService() {
        System.out.println("LAZY bean created NOW (not at startup)");
        return new EmailService("Lazy-" + applicationName);
    }


    // ========================================================================
    // COMPLEX BEAN WITH MULTIPLE DEPENDENCIES
    // ========================================================================

    /**
     * Demonstrates complex bean with multiple dependency injection techniques
     * Combines: @Value, @Qualifier, and other beans as parameters
     */
    @Bean
    public String applicationInfo(
            @Value("${app.name}") String name,
            @Value("${app.version}") String version,
            @Value("${app.environment}") String environment,
            AppProperties properties,
            @Qualifier("emailService") NotificationService emailService,
            ApiClient apiClientWithProperties) {

        return String.format(
                """
                Application: %s
                Version: %s
                Environment: %s
                Email Enabled: %s
                SMS Enabled: %s
                API Base URL: %s
                Notification Type: %s
                """,
                name,
                version,
                environment,
                properties.getFeature().isEmailEnabled(),
                properties.getFeature().isSmsEnabled(),
                apiClientWithProperties.getBaseUrl(),
                emailService.getServiceType()
        );
    }
}

