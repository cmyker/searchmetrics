package com.searchmetrics.codingtask.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartedEventListener {

    private static final Logger log = LoggerFactory.getLogger(StartedEventListener.class);

    @Profile("dev")
    @EventListener
    public void onStarted(ApplicationStartedEvent applicationStartedEvent) {
        Environment env = applicationStartedEvent.getApplicationContext().getEnvironment();
        log.info("swagger-ui access URL: , http://localhost:{}/swagger-ui/", env.getProperty("server.port"));
    }

}
