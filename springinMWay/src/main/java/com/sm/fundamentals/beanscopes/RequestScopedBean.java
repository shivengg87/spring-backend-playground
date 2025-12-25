package com.sm.fundamentals.beanscopes;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
// Marks this class as a Spring-managed component
@Component
/*
 * Defines REQUEST scope:
 * - One bean instance per HTTP request
 * - Bean is created when request starts
 * - Bean is destroyed when request ends
 *
 * proxyMode is REQUIRED because this bean is injected
 * into singleton-scoped components (like controllers).
 */
@Scope(
        value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
public class RequestScopedBean {
    // Unique identifier to prove a new instance per request
    private String requestId;
    /*
     * Called immediately after the bean is created
     * and all dependencies are injected.
     * Executes ONCE per HTTP request.
     */
    @PostConstruct
    public void init() {
        requestId = UUID.randomUUID().toString();
        System.out.println("New request scope bean created: " + requestId);
    }

    // Returns the request-specific identifier
    public String getRequestId() {
        return requestId;
    }
    /*
     * Called when the HTTP request is completed
     * and the request-scoped bean is destroyed.
     */
    @PreDestroy
    public void destroy() {
        System.out.println("Request scope bean destroyed: " + requestId);
    }
}