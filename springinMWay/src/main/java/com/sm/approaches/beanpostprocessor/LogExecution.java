package com.sm.approaches.beanpostprocessor;

import java.lang.annotation.*;

/**
 * Marks beans whose method executions should be logged
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogExecution {
    boolean logArgs() default true;
    boolean logResult() default true;
}

