package com.searchmetrics.codingtask.web.rest;

import com.searchmetrics.codingtask.service.FetchRateService;
import com.searchmetrics.codingtask.service.RateService;
import com.searchmetrics.codingtask.service.dto.RateHistoryDTO;
import io.swagger.annotations.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RateController {

    private final RateService rateService;

    private final FetchRateService fetchRateService;

    public RateController(RateService rateService, FetchRateService fetchRateService) {
        this.rateService = rateService;
        this.fetchRateService = fetchRateService;
    }

    @GetMapping(value = "/rates", produces = "application/json")
    public ResponseEntity<List<RateHistoryDTO>> rates(
        @RequestParam(required = true) @ApiParam(value = "example: 2021-02-23T18:57:05") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam(required = true) @ApiParam(value = "example: 2021-02-23T18:57:05") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "20") int size
    ) {
        var paged = rateService.getRateHistory(
            PageRequest.of(page, size),
            startDate.toInstant(ZoneOffset.UTC),
            endDate.toInstant(ZoneOffset.UTC)
        );
        var headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), paged);
        return new ResponseEntity<>(paged.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/rates/latest", produces = "text/plain")
    public ResponseEntity<String> latestRate() {
        return rateService
            .getLatestRate()
            .map(rate -> ResponseEntity.ok(rate.toPlainString()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        ;
    }

    @PutMapping("/fetch")
    public ResponseEntity<Void> fetch() {
        try {
            fetchRateService.fetch();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching", e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
