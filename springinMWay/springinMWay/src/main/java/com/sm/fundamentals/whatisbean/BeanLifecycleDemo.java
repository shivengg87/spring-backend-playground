package com.sm.fundamentals.whatisbean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Complete Bean Lifecycle Demonstration
 * Shows all lifecycle callbacks in order
 */
@Component
public class BeanLifecycleDemo implements InitializingBean, DisposableBean,
        BeanNameAware, ApplicationContextAware {

    private String beanName;
    private ApplicationContext applicationContext;

    // 1. Constructor
    public BeanLifecycleDemo() {
        System.out.println("1. Constructor called - Bean instantiation");
    }

    // 2. Setter for dependency injection (if using setter injection)
    public void setDependency(String dependency) {
        System.out.println("2. Dependencies populated");
    }

    // 3. BeanNameAware interface
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("3. setBeanName() called - Bean name: " + name);
    }

    // 4. ApplicationContextAware interface
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("4. setApplicationContext() called");
    }

    // 5. @PostConstruct annotation
    @PostConstruct
    public void postConstruct() {
        System.out.println("5. @PostConstruct called - Custom initialization");
    }

    // 6. InitializingBean interface
    @Override
    public void afterPropertiesSet() {
        System.out.println("6. afterPropertiesSet() called - Bean fully initialized");
    }

    // 7. Custom init method (if defined in @Bean)
    public void customInit() {
        System.out.println("7. Custom init method called");
    }

    // Bean is now ready to use
    public void businessMethod() {
        System.out.println("--- Bean is ready! Executing business logic ---");
    }

    // 8. @PreDestroy annotation (when context is closing)
    @PreDestroy
    public void preDestroy() {
        System.out.println("8. @PreDestroy called - Cleanup before destruction");
    }

    // 9. DisposableBean interface
    @Override
    public void destroy() {
        System.out.println("9. destroy() called - Bean destruction");
    }

    // 10. Custom destroy method (if defined in @Bean)
    public void customDestroy() {
        System.out.println("10. Custom destroy method called");
    }
}