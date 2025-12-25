package com.sm.approaches.beanpostprocessor;

import java.lang.annotation.*;

/**
 * Marks fields that should be encrypted/secured
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecureField {
}