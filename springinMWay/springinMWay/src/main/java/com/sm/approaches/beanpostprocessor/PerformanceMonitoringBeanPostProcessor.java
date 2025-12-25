package com.sm.approaches.beanpostprocessor;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * METHOD-LEVEL ANNOTATION BeanPostProcessor
 *
 * Uses CGLIB to create proxy for classes (not just interfaces)
 * Monitors performance of methods marked with @PerformanceMonitored
 */
@Component
public class PerformanceMonitoringBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        // Check if any method has @PerformanceMonitored
        Method[] methods = bean.getClass().getDeclaredMethods();
        boolean hasMonitoredMethods = false;

        for (Method method : methods) {
            if (method.isAnnotationPresent(PerformanceMonitored.class)) {
                hasMonitoredMethods = true;
                break;
            }
        }

        if (hasMonitoredMethods) {
            System.out.println("   [PERF] Creating performance proxy for: " +
                    beanName);
            return createPerformanceProxy(bean);
        }

        return bean;
    }

    /**
     * Creates CGLIB proxy for performance monitoring
     * CGLIB can proxy classes, not just interfaces
     */
    private Object createPerformanceProxy(Object target) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new PerformanceMethodInterceptor(target));

        return enhancer.create();
    }

    /**
     * CGLIB MethodInterceptor for performance monitoring
     */
    private static class PerformanceMethodInterceptor implements MethodInterceptor {

        private final Object target;

        public PerformanceMethodInterceptor(Object target) {
            this.target = target;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args,
                                MethodProxy proxy) throws Throwable {

            // Check if method has @PerformanceMonitored
            Method targetMethod = target.getClass().getMethod(
                    method.getName(),
                    method.getParameterTypes()
            );

            if (targetMethod.isAnnotationPresent(PerformanceMonitored.class)) {

                // Monitor performance
                long startTime = System.nanoTime();
                Object result = method.invoke(target, args);
                long endTime = System.nanoTime();

                double durationMs = (endTime - startTime) / 1_000_000.0;

                System.out.println("\n   [PERF] Method: " + method.getName());
                System.out.println("   [PERF] Duration: " +
                        String.format("%.2f", durationMs) + "ms");

                return result;
            }

            // If not monitored, just invoke normally
            return method.invoke(target, args);
        }
    }
}
