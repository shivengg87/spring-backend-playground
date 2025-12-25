package com.sm.fundamentals.beanscopes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request-scope")
public class RequestScopeController {

    /*
     * Injected as a PROXY object.
     * Actual RequestScopedBean instance is resolved
     * dynamically for each HTTP request.
     */
    private final RequestScopedBean requestScopedBean;

    // Constructor injection (recommended)
    public RequestScopeController(RequestScopedBean requestScopedBean) {
        this.requestScopedBean = requestScopedBean;
    }

    /*
     * Each HTTP request to this endpoint:
     * - Creates a NEW RequestScopedBean instance
     * - Uses a unique requestId
     * - Destroys the bean after request completion
     */
    @GetMapping
    public String testRequestScope() {
        return "Request ID: " + requestScopedBean.getRequestId();
    }
}