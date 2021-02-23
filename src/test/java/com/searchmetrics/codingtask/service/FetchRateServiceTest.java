package com.searchmetrics.codingtask.service;

import com.searchmetrics.codingtask.domain.RateHistory;
import com.searchmetrics.codingtask.respository.RateHistoryRepository;
import com.searchmetrics.codingtask.scheduler.FetchRateScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FetchRateServiceTest {

    @Mock
    RestTemplate restTemplateMock;

    @Mock
    RateHistoryRepository rateHistoryRepositoryMock;

    @Captor
    ArgumentCaptor<RateHistory> saveCaptor;

    private final static String URL = "http://test";

    @Test
    void fetch() {
        when(restTemplateMock.getForObject(anyString(), eq(String.class))).thenAnswer(invocation -> "0.00002135");

        var fetchRateScheduler = new FetchRateService(restTemplateMock, rateHistoryRepositoryMock, URL);
        fetchRateScheduler.fetch();

        verify(rateHistoryRepositoryMock, times(1)).save(saveCaptor.capture());
        assertEquals(saveCaptor.getValue().getRate(), new BigDecimal("0.00002135"));
    }

    @Test
    void fetchRateResponseInvalid() {
        when(restTemplateMock.getForObject(anyString(), eq(String.class))).thenAnswer(invocation -> "AAbb");

        var fetchRateScheduler = new FetchRateService(restTemplateMock, rateHistoryRepositoryMock, URL);

        assertThrows(NumberFormatException.class, fetchRateScheduler::fetch);
    }
}