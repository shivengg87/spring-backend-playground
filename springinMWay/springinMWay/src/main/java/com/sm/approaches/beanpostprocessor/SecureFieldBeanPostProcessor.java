package com.sm.approaches.beanpostprocessor;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Base64;

/**
 * FIELD MODIFICATION BeanPostProcessor
 *
 * Automatically encrypts fields marked with @SecureField
 * Demonstrates modifying bean state during initialization
 */
@Component
public class SecureFieldBeanPostProcessor implements BeanPostProcessor {

    @Value("${app.secure.encryption-key}")
    private String encryptionKey;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        // Scan all fields in the bean
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            // Check if field has @SecureField annotation
            if (field.isAnnotationPresent(SecureField.class)) {

                // Make private field accessible
                field.setAccessible(true);

                try {
                    // Get current value
                    Object value = field.get(bean);

                    if (value instanceof String) {
                        String plainText = (String) value;

                        // Simple encryption (Base64 + key for demo)
                        String encrypted = encrypt(plainText);

                        // Set encrypted value back
                        field.set(bean, encrypted);

                        System.out.println("   [SECURE] Encrypted field: " +
                                field.getName() + " in " + beanName);
                    }

                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to encrypt field: " +
                            field.getName(), e);
                } finally {
                    field.setAccessible(false);
                }
            }
        }

        return bean;
    }

    // Simple encryption for demonstration (NOT production-grade!)
    private String encrypt(String plainText) {
        String combined = plainText + ":" + encryptionKey;
        return Base64.getEncoder().encodeToString(combined.getBytes());
    }
}
