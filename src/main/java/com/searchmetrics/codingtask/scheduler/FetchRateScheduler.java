package com.searchmetrics.codingtask.scheduler;

import com.searchmetrics.codingtask.service.FetchRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchRateScheduler {

    private final FetchRateService fetchRateService;

    public FetchRateScheduler(FetchRateService fetchRateService) {
        this.fetchRateService = fetchRateService;
    }

    @Scheduled(cron = "${application.fetch-rate-cron}")
    public void schedule() {
        fetchRateService.fetch();
    }

}
