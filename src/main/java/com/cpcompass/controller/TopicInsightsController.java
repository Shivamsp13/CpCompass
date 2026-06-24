package com.cpcompass.controller;

import com.cpcompass.dto.topic.TopicInsightsResponse;
import com.cpcompass.service.TopicInsightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topic-insights")
@RequiredArgsConstructor
public class TopicInsightsController {

    private final TopicInsightsService topicInsightsService;

    @GetMapping
    public TopicInsightsResponse getTopicInsights() {

        return topicInsightsService
                .getTopicInsights();
    }
}