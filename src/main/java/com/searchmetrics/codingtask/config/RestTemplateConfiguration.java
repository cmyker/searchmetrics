package com.searchmetrics.codingtask.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate fetchRateRestTemplate(ApplicationProperties applicationProperties, RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(applicationProperties.getFetchRateConnectionTimeoutSeconds()))
            .build()
        ;
    }

}
