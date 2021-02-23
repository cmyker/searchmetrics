package com.searchmetrics.codingtask.service;

import com.searchmetrics.codingtask.domain.RateHistory;
import com.searchmetrics.codingtask.respository.RateHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class FetchRateService {

    private static final Logger log = LoggerFactory.getLogger(FetchRateService.class);

    private final RestTemplate restTemplate;

    private final RateHistoryRepository rateHistoryRepository;

    private final String url;

    public FetchRateService(
        @Qualifier("fetchRateRestTemplate") RestTemplate restTemplate,
        RateHistoryRepository rateHistoryRepository,
        @Value("${application.fetch-rate-url}") String url
    ) {
        this.restTemplate = restTemplate;
        this.rateHistoryRepository = rateHistoryRepository;
        this.url = url;
    }

    public void fetch() {
        log.debug("Fetching rate from URL {}", url);
        var responseString = Objects.requireNonNull(restTemplate.getForObject(url, String.class));
        log.debug("Got response string {}", responseString);
        var rate = new BigDecimal(responseString);
        var ratesHistory = new RateHistory();
        ratesHistory.setRate(rate);
        rateHistoryRepository.save(ratesHistory);
    }

}
