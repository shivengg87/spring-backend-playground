package com.sm.fundamentals.beanscopes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Prototype Scope
 * - New instance created every time bean is requested
 * - Not managed after creation (Spring doesn't track it)
 * - Suitable for stateful objects
 * - More memory overhead
 */
@Component
@Scope("prototype")
public class PrototypeBean {

    private final String instanceId;
    private int counter = 0;

    public PrototypeBean() {
        this.instanceId = UUID.randomUUID().toString();
        System.out.println("New PrototypeBean created: " + instanceId);
    }

    public void increment() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public String getInstanceId() {
        return instanceId;
    }
}