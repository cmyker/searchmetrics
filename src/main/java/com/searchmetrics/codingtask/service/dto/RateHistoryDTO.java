package com.searchmetrics.codingtask.service.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class RateHistoryDTO {

    private final String rate;

    private final String date;

    public RateHistoryDTO(String rate, String date) {
        this.rate = rate;
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }
}
