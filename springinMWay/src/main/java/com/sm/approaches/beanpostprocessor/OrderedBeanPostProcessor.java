package com.sm.approaches.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * ORDERED BeanPostProcessor
 *
 * Demonstrates controlling the order of BeanPostProcessor execution
 * Lower values have higher priority
 *
 * Default order is Ordered.LOWEST_PRECEDENCE
 */
@Component
public class OrderedBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (beanName.contains("Service")) {
            System.out.println("   [ORDERED-BPP] First processor (Order: " +
                    getOrder() + ")");
        }

        return bean;
    }

    @Override
    public int getOrder() {
        return 1; // Will execute before processors with higher order values
    }
}