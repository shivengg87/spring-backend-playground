package com.sm.fundamentals.beanscopes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Singleton Scope Bean (Spring Default)
 *
 * - Exactly ONE instance is created per Spring IoC container
 * - Same instance is shared across all injections and threads
 * - Suitable for stateless services
 * - MUST be thread-safe if mutable state is present
 */
@Component
@Scope("singleton") // Default scope; explicit here only for clarity
public class SingletonBean {
    /*
     * Shared mutable state.
     * Since singleton beans are accessed by multiple threads,
     * this variable can cause race conditions if not handled properly.
     */
    private int counter = 0; // Shared state - DANGEROUS!

    /*
     * Constructor is called ONLY ONCE when the Spring container
     * creates this singleton bean during application startup.
     */
    public SingletonBean() {
        System.out.println("SingletonBean instance created at: " + System.currentTimeMillis());
    }

    /*
     * Increments and returns the counter value.
     * WARNING: This operation is NOT thread-safe and may produce
     * inconsistent results under concurrent access.
     */
    public int increment() {
        return ++counter; // Not thread-safe!
    }

    /*
     * Returns the current counter value.
     * Value is shared across all callers since this is a singleton bean.
     */
    public int getCounter() {
        return counter;
    }

    /*
     * Returns a unique identity hash of the bean instance.
     * Useful for demonstrating that only one instance exists
     * across multiple getBean() calls.
     */
    public String getInstanceId() {
        return Integer.toHexString(System.identityHashCode(this));
    }
}