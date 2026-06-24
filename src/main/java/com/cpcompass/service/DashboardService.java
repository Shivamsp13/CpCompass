package com.cpcompass.service;

import com.cpcompass.dto.dashboard.DashboardResponse;
import com.cpcompass.entity.RatingBandAnalytics;
import com.cpcompass.entity.TopicAnalytics;
import com.cpcompass.entity.User;
import com.cpcompass.repository.ContestRepository;
import com.cpcompass.repository.RatingBandAnalyticsRepository;
import com.cpcompass.repository.SubmissionRepository;
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
public class DashboardService {

    private final UserRepository userRepository;
    private final TopicAnalyticsRepository topicAnalyticsRepository;
    private final RatingBandAnalyticsRepository ratingBandAnalyticsRepository;
    private final ContestRepository contestRepository;
    private final SubmissionRepository submissionRepository;

    public DashboardResponse getDashboard() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        List<TopicAnalytics> topics =
                topicAnalyticsRepository
                        .findByUserId(user.getId());

        List<RatingBandAnalytics> ratingBands =
                ratingBandAnalyticsRepository
                        .findByUserId(user.getId());

        long totalContests =
                contestRepository
                        .findByUserId(user.getId())
                        .size();

        List<com.cpcompass.entity.Submission> submissions =
                submissionRepository
                        .findByUserIdOrderBySubmissionTimeDesc(
                                user.getId()
                        );

        long totalSubmissions =
                submissions.size();

        long totalSolved =
                submissions.stream()
                        .filter(
                                submission ->
                                        Boolean.TRUE.equals(
                                                submission.getSolved()
                                        )
                        )
                        .count();

        return DashboardResponse.builder()
                .currentRating(
                        user.getCurrentRating()
                )
                .maxRating(
                        user.getMaxRating()
                )
                .strongTopics(
                        getStrongTopics(topics)
                )
                .weakTopics(
                        getWeakTopics(topics)
                )
                .strongRatingBands(
                        getStrongRatingBands(
                                ratingBands
                        )
                )
                .growthZone(
                        getGrowthZone(
                                ratingBands
                        )
                )
                .weakRatingBands(
                        getWeakRatingBands(
                                ratingBands
                        )
                )
                .totalContests(
                        totalContests
                )
                .totalSubmissions(
                        totalSubmissions
                )
                .totalSolved(
                        totalSolved
                )
                .build();
    }

    private List<String> getStrongTopics(
            List<TopicAnalytics> topics
    ) {

        return topics.stream()
                .filter(
                        topic ->
                                topic.getAttempts() >= 10
                )
                .sorted(
                        Comparator.comparing(
                                        TopicAnalytics::getAcceptanceRate
                                )
                                .reversed()
                )
                .limit(3)
                .map(
                        TopicAnalytics::getTopic
                )
                .toList();
    }

    private List<String> getWeakTopics(
            List<TopicAnalytics> topics
    ) {

        return topics.stream()
                .filter(
                        topic ->
                                topic.getAttempts() >= 10
                )
                .sorted(
                        Comparator.comparing(
                                TopicAnalytics::getAcceptanceRate
                        )
                )
                .limit(3)
                .map(
                        TopicAnalytics::getTopic
                )
                .toList();
    }

    private List<String> getStrongRatingBands(
            List<RatingBandAnalytics> bands
    ) {

        return bands.stream()
                .sorted(
                        Comparator.comparing(
                                        RatingBandAnalytics::getAcceptanceRate
                                )
                                .reversed()
                )
                .limit(3)
                .map(
                        RatingBandAnalytics::getRatingBand
                )
                .toList();
    }

    private List<String> getWeakRatingBands(
            List<RatingBandAnalytics> bands
    ) {

        return bands.stream()
                .sorted(
                        Comparator.comparing(
                                RatingBandAnalytics::getAcceptanceRate
                        )
                )
                .limit(3)
                .map(
                        RatingBandAnalytics::getRatingBand
                )
                .toList();
    }

    private String getGrowthZone(
            List<RatingBandAnalytics> bands
    ) {

        return bands.stream()
                .filter(
                        band ->
                                band.getAcceptanceRate() >= 40
                                        && band.getAcceptanceRate() <= 70
                )
                .max(
                        Comparator.comparing(
                                this::bandOrder
                        )
                )
                .map(
                        RatingBandAnalytics::getRatingBand
                )
                .orElse("N/A");
    }

    private int bandOrder(
            RatingBandAnalytics band
    ) {

        return switch (
                band.getRatingBand()
                ) {

            case "800-1000" -> 1;

            case "1000-1200" -> 2;

            case "1200-1400" -> 3;

            case "1400-1600" -> 4;

            case "1600-1800" -> 5;

            case "1800+" -> 6;

            default -> 0;
        };
    }
}