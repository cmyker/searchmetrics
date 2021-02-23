package com.searchmetrics.codingtask.service;

import com.searchmetrics.codingtask.domain.RateHistory;
import com.searchmetrics.codingtask.respository.RateHistoryRepository;
import com.searchmetrics.codingtask.service.dto.RateHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
public class RateService {

    private final RateHistoryRepository rateHistoryRepository;

    public RateService(RateHistoryRepository rateHistoryRepository) {
        this.rateHistoryRepository = rateHistoryRepository;
    }

    public Optional<BigDecimal> getLatestRate() {
        return rateHistoryRepository
            .findTopByOrderByCreatedAtDesc()
            .map(RateHistory::getRate)
        ;
    }

    public Page<RateHistoryDTO> getRateHistory(Pageable pageable, Instant createdAtStart, Instant createdAtEnd) {
        return rateHistoryRepository
            .findAllByCreatedAtBetween(createdAtStart, createdAtEnd, pageable)
            .map(rateHistory -> new RateHistoryDTO(rateHistory.getRate().toPlainString(), rateHistory.getCreatedAt().toString()))
        ;
    }

}
