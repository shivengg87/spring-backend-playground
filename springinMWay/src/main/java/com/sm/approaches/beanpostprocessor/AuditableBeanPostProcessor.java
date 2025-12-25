package com.sm.approaches.beanpostprocessor;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;

/**
 * PROXY CREATION BeanPostProcessor
 *
 * Wraps beans annotated with @Auditable in a dynamic proxy
 * Logs all method calls with timestamp and arguments
 *
 * This is a common pattern for:
 * - Auditing
 * - Transaction management
 * - Security checks
 * - Performance monitoring
 */
@Component
public class AuditableBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        // Check if bean class has @Auditable annotation
        if (bean.getClass().isAnnotationPresent(Auditable.class)) {

            Auditable annotation = bean.getClass().getAnnotation(Auditable.class);
            String description = annotation.value();

            System.out.println("   [AUDIT] Creating audit proxy for: " + beanName +
                    " (" + description + ")");

            // Create and return a proxy that wraps the original bean
            return createAuditProxy(bean, beanName);
        }

        return bean;
    }

    /**
     * Creates a dynamic proxy that intercepts all method calls
     */
    private Object createAuditProxy(Object target, String beanName) {

        Class<?> targetClass = target.getClass();
        Class<?>[] interfaces = targetClass.getInterfaces();

        // If class implements interfaces, use them for proxy
        if (interfaces.length > 0) {
            return Proxy.newProxyInstance(
                    targetClass.getClassLoader(),
                    interfaces,
                    new AuditInvocationHandler(target, beanName)
            );
        }

        // If no interfaces, return original (could use CGLIB here)
        return target;
    }

    /**
     * InvocationHandler that adds audit logging to every method call
     */
    private static class AuditInvocationHandler implements InvocationHandler {

        private final Object target;
        private final String beanName;

        public AuditInvocationHandler(Object target, String beanName) {
            this.target = target;
            this.beanName = beanName;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {

            // Log before method execution
            System.out.println("\n   [AUDIT] " + LocalDateTime.now());
            System.out.println("   [AUDIT] Bean: " + beanName);
            System.out.println("   [AUDIT] Method: " + method.getName());

            if (args != null && args.length > 0) {
                System.out.print("   [AUDIT] Arguments: ");
                for (int i = 0; i < args.length; i++) {
                    System.out.print(args[i]);
                    if (i < args.length - 1) System.out.print(", ");
                }
                System.out.println();
            }

            // Execute actual method
            long startTime = System.currentTimeMillis();
            Object result = method.invoke(target, args);
            long endTime = System.currentTimeMillis();

            // Log after method execution
            System.out.println("   [AUDIT] Execution time: " +
                    (endTime - startTime) + "ms");
            System.out.println("   [AUDIT] Result: " + result);

            return result;
        }
    }
}
