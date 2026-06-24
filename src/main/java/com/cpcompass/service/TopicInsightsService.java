package com.cpcompass.service;

import com.cpcompass.dto.topic.TopicInsightDto;
import com.cpcompass.dto.topic.TopicInsightsResponse;
import com.cpcompass.entity.TopicAnalytics;
import com.cpcompass.entity.User;
import com.cpcompass.repository.TopicAnalyticsRepository;
import com.cpcompass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicInsightsService {

    private final UserRepository userRepository;
    private final TopicAnalyticsRepository topicAnalyticsRepository;

    public TopicInsightsResponse getTopicInsights() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        List<TopicInsightDto> topics =
                topicAnalyticsRepository
                        .findByUserId(user.getId())
                        .stream()
                        .sorted(
                                Comparator.comparing(
                                                TopicAnalytics::getAcceptanceRate
                                        )
                                        .reversed()
                        )
                        .map(
                                topic ->
                                        TopicInsightDto.builder()
                                                .topic(
                                                        topic.getTopic()
                                                )
                                                .attempts(
                                                        topic.getAttempts()
                                                )
                                                .accepted(
                                                        topic.getAccepted()
                                                )
                                                .acceptanceRate(
                                                        topic.getAcceptanceRate()
                                                )
                                                .averageAttemptsBeforeAc(
                                                        topic.getAverageAttemptsBeforeAc()
                                                )
                                                .build()
                        )
                        .toList();

        return TopicInsightsResponse.builder()
                .topics(topics)
                .build();
    }
}