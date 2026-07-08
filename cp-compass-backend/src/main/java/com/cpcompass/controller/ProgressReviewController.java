package com.cpcompass.controller;

import com.cpcompass.dto.progressreview.ProgressReviewResponse;
import com.cpcompass.service.ProgressReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress-review")
@RequiredArgsConstructor
public class ProgressReviewController {

    private final ProgressReviewService progressReviewService;

    @GetMapping
    public ProgressReviewResponse getProgressReview(
            @RequestParam(defaultValue = "30")
            Integer days
    ) {

        return progressReviewService.getProgressReview(
                days
        );

    }

}