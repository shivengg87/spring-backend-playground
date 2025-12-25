package com.sm.approaches.configurationclass;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Type-safe configuration properties class
 * Maps properties with prefix "app" from application.properties
 * Provides compile-time type safety and IDE auto-completion
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
    private String version;
    private String environment;
    private ApiConfig api = new ApiConfig();
    private FeatureFlags feature = new FeatureFlags();

    // Nested static class for API configuration
    public static class ApiConfig {
        private String baseUrl;
        private int timeout;
        private int retryCount;

        // Getters and Setters
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
        public int getTimeout() { return timeout; }
        public void setTimeout(int timeout) { this.timeout = timeout; }
        public int getRetryCount() { return retryCount; }
        public void setRetryCount(int retryCount) { this.retryCount = retryCount; }
    }

    // Nested static class for feature flags
    public static class FeatureFlags {
        private boolean emailEnabled;
        private boolean smsEnabled;

        // Getters and Setters
        public boolean isEmailEnabled() { return emailEnabled; }
        public void setEmailEnabled(boolean emailEnabled) { this.emailEnabled = emailEnabled; }
        public boolean isSmsEnabled() { return smsEnabled; }
        public void setSmsEnabled(boolean smsEnabled) { this.smsEnabled = smsEnabled; }
    }

    // Getters and Setters for main properties
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }
    public ApiConfig getApi() { return api; }
    public void setApi(ApiConfig api) { this.api = api; }
    public FeatureFlags getFeature() { return feature; }
    public void setFeature(FeatureFlags feature) { this.feature = feature; }
}
