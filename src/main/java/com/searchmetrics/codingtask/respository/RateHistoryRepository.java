package com.searchmetrics.codingtask.respository;

import com.searchmetrics.codingtask.domain.RateHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RateHistoryRepository extends MongoRepository<RateHistory, String> {

    Optional<RateHistory> findTopByOrderByCreatedAtDesc();

    Page<RateHistory> findAllByCreatedAtBetween(Instant startDate, Instant endDate, Pageable pageable);
}
