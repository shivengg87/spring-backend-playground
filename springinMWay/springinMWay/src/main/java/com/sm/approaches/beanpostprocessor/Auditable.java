package com.sm.approaches.beanpostprocessor;
import java.lang.annotation.*;
/*
 * BeanPostProcessor is a powerful Spring interface that allows you to
 * hook into the Spring bean lifecycle and modify beans before and after
 * their initialization.
 *
 * LIFECYCLE ORDER:
 * 1. Bean Instantiation (Constructor called)
 * 2. Dependency Injection (Properties set)
 * 3. postProcessBeforeInitialization() <- BeanPostProcessor
 * 4. @PostConstruct / InitializingBean.afterPropertiesSet()
 * 5. Custom init-method
 * 6. postProcessAfterInitialization() <- BeanPostProcessor
 * 7. Bean Ready for Use
 * 8. @PreDestroy / DisposableBean.destroy()
 * 9. Custom destroy-method
 */


/**
 * Custom annotation to mark beans that should be audited
 * BeanPostProcessor will detect this and wrap bean with audit proxy
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {
    String value() default ""; // Optional description
}
