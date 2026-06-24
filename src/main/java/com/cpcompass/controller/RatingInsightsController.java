package com.cpcompass.controller;

import com.cpcompass.dto.rating.RatingInsightsResponse;
import com.cpcompass.service.RatingInsightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating-insights")
@RequiredArgsConstructor
public class RatingInsightsController {

    private final RatingInsightsService ratingInsightsService;

    @GetMapping
    public RatingInsightsResponse getRatingInsights() {

        return ratingInsightsService
                .getRatingInsights();
    }
}