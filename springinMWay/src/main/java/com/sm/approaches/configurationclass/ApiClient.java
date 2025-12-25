package com.sm.approaches.configurationclass;

public class ApiClient {

    private final String baseUrl;
    private final int timeout;
    private final int retryCount;

    public ApiClient(String baseUrl, int timeout, int retryCount) {
        this.baseUrl = baseUrl;
        this.timeout = timeout;
        this.retryCount = retryCount;
    }

    public String callApi(String endpoint) {
        return String.format("Calling %s%s (timeout: %dms, retries: %d)",
                baseUrl, endpoint, timeout, retryCount);
    }

    // Getters for verification
    public String getBaseUrl() { return baseUrl; }
    public int getTimeout() { return timeout; }
    public int getRetryCount() { return retryCount; }
}
