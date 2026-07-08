package com.cpcompass.controller;

import com.cpcompass.dto.recommendation.RecommendationRequest;
import com.cpcompass.dto.recommendation.RecommendationResponse;
import com.cpcompass.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public RecommendationResponse generateRecommendation(
            @RequestBody RecommendationRequest request
    ) {

        return recommendationService.generateRecommendation(
                request
        );

    }

    @GetMapping("/today")
    public RecommendationResponse getTodayRecommendation() {

        return recommendationService
                .getTodayRecommendation();

    }

    @PostMapping("/generate-another")
    public RecommendationResponse generateAnotherRecommendation() {

        return recommendationService
                .generateAnotherRecommendation();

    }

}