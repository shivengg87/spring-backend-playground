package com.sm.approaches.beanpostprocessor;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * CONDITIONAL LOGGING BeanPostProcessor
 *
 * Logs method executions based on annotation configuration
 * Demonstrates reading annotation attributes
 */
@Component
public class LogExecutionBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        Class<?> beanClass = bean.getClass();

        if (beanClass.isAnnotationPresent(LogExecution.class)) {

            LogExecution annotation = beanClass.getAnnotation(LogExecution.class);

            System.out.println("   [LOG] Creating logging proxy for: " + beanName);
            System.out.println("   [LOG] Log args: " + annotation.logArgs());
            System.out.println("   [LOG] Log result: " + annotation.logResult());

            return createLoggingProxy(bean, annotation);
        }

        return bean;
    }

    private Object createLoggingProxy(Object target, LogExecution config) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {

            System.out.println("\n   [LOG] Executing: " + method.getName());

            if (config.logArgs() && args != null && args.length > 0) {
                System.out.println("   [LOG] Arguments: " +
                        Arrays.toString(args));
            }

            Object result = method.invoke(target, args);

            if (config.logResult() && result != null) {
                System.out.println("   [LOG] Result: " + result);
            }

            return result;
        });

        return enhancer.create();
    }
}

