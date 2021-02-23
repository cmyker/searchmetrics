package com.searchmetrics.codingtask.web.rest;

import com.searchmetrics.codingtask.CodingtaskApplication;
import com.searchmetrics.codingtask.domain.RateHistory;
import com.searchmetrics.codingtask.respository.RateHistoryRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = CodingtaskApplication.class)
@AutoConfigureMockMvc
class RateControllerIT {

    @Autowired
    private RateHistoryRepository rateHistoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void createRecords() {
        var rateHistory = new RateHistory();
        rateHistory.setRate(new BigDecimal("0.000023"));
        rateHistory.setCreatedAt(Instant.parse("2021-02-23T18:57:00Z"));
        rateHistoryRepository.save(rateHistory);

        rateHistory = new RateHistory();
        rateHistory.setRate(new BigDecimal("0.000024"));
        rateHistory.setCreatedAt(Instant.parse("2021-02-23T18:58:00Z"));
        rateHistoryRepository.save(rateHistory);
    }

    @Test
    void rates() throws Exception {
        mockMvc
            .perform(
                get("/api/rates")
                    .param("startDate", "2021-02-23T18:00:00")
                    .param("endDate", "2021-02-23T19:00:00")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].rate").value("0.000023"))
            .andExpect(jsonPath("$.[1].rate").value("0.000024"))
        ;
    }

    @Test
    void latestRate() throws Exception {
        mockMvc
            .perform(get("/api/rates/latest"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("0.000024"))
        ;
    }
}