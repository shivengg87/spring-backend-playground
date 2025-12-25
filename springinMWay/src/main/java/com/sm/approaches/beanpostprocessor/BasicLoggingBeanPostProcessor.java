package com.sm.approaches.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * BASIC BeanPostProcessor Example
 *
 * Demonstrates the two key methods:
 * - postProcessBeforeInitialization: Called BEFORE @PostConstruct/init-method
 * - postProcessAfterInitialization: Called AFTER @PostConstruct/init-method
 *
 * This processor runs for EVERY bean in the application context
 */
@Component
public class BasicLoggingBeanPostProcessor implements BeanPostProcessor {

    /**
     * Called BEFORE initialization callbacks (@PostConstruct, init-method)
     *
     * @param bean - The new bean instance
     * @param beanName - The name of the bean
     * @return The bean instance to use (original or wrapped)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        // Only log our custom services to avoid clutter
        if (beanName.contains("Service")) {
            System.out.println("   [BPP-BEFORE] Processing: " + beanName);
        }

        // MUST return the bean (or a proxy/wrapper)
        return bean;
    }

    /**
     * Called AFTER initialization callbacks (@PostConstruct, init-method)
     * This is where you typically wrap beans with proxies
     *
     * @param bean - The initialized bean instance
     * @param beanName - The name of the bean
     * @return The bean instance to use (original or wrapped)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (beanName.contains("Service")) {
            System.out.println("   [BPP-AFTER] Completed: " + beanName);
        }

        return bean;
    }
}
