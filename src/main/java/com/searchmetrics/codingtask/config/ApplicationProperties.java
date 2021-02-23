package com.searchmetrics.codingtask.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String fetchRateCron;

    private String fetchRateUrl;

    private int fetchRateConnectionTimeoutSeconds;

    public String getFetchRateCron() {
        return fetchRateCron;
    }

    public void setFetchRateCron(String fetchRateCron) {
        this.fetchRateCron = fetchRateCron;
    }

    public String getFetchRateUrl() {
        return fetchRateUrl;
    }

    public void setFetchRateUrl(String fetchRateUrl) {
        this.fetchRateUrl = fetchRateUrl;
    }

    public int getFetchRateConnectionTimeoutSeconds() {
        return fetchRateConnectionTimeoutSeconds;
    }

    public void setFetchRateConnectionTimeoutSeconds(int fetchRateConnectionTimeoutSeconds) {
        this.fetchRateConnectionTimeoutSeconds = fetchRateConnectionTimeoutSeconds;
    }
}
