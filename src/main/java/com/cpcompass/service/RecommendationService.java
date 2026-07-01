package com.cpcompass.service;

import com.cpcompass.client.CodeforcesClient;
import com.cpcompass.dto.recommendation.RecommendationRequest;
import com.cpcompass.dto.recommendation.RecommendationResponse;
import com.cpcompass.dto.sync.CfProblemDto;
import com.cpcompass.dto.sync.CfProblemsetResponse;
import com.cpcompass.entity.RatingBandAnalytics;
import com.cpcompass.entity.Submission;
import com.cpcompass.entity.TopicAnalytics;
import com.cpcompass.entity.User;
import com.cpcompass.recommendation.RecommendationEngine;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RedisRecommendationService redisRecommendationService;
    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final TopicAnalyticsRepository topicAnalyticsRepository;
    private final RatingBandAnalyticsRepository ratingBandAnalyticsRepository;
    private final CodeforcesClient codeforcesClient;
    private final RecommendationEngine recommendationEngine;

    public RecommendationResponse generateRecommendation(
            RecommendationRequest request
    ) {
//        long start = System.currentTimeMillis();
        User user = getCurrentUser();

        Set<String> solvedProblems =
                getSolvedProblems(user);

        List<String> weakTopics =
                getWeakTopics(user);

        int[] ratingRange =
                getGrowthZoneRange(user);

        CfProblemsetResponse response =
                codeforcesClient.getProblemset();

        if (response == null ||
                response.getResult() == null ||
                response.getResult().getProblems() == null) {

            throw new RuntimeException(
                    "Unable to fetch Codeforces problemset."
            );
        }

        CfProblemDto recommendedProblem =
                recommendationEngine.generateRecommendation(
                        response.getResult().getProblems(),
                        solvedProblems,
                        weakTopics,
                        ratingRange[0],
                        ratingRange[1],
                        request
                );

//        System.out.println(
//                "Recommendation took: "
//                        + (System.currentTimeMillis() - start)
//                        + " ms"
//        );
        return RecommendationResponse.builder()
                .contestId(
                        recommendedProblem.getContestId()
                )
                .problemIndex(
                        recommendedProblem.getIndex()
                )
                .problemName(
                        recommendedProblem.getName()
                )
                .rating(
                        recommendedProblem.getRating()
                )
                .tags(
                        recommendedProblem.getTags()
                )
                .url(
                        "https://codeforces.com/problemset/problem/"
                                + recommendedProblem.getContestId()
                                + "/"
                                + recommendedProblem.getIndex()
                )
                .build();
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found."
                        )
                );
    }

    private Set<String> getSolvedProblems(
            User user
    ) {

        return submissionRepository
                .findByUserIdOrderBySubmissionTimeDesc(
                        user.getId()
                )
                .stream()
                .filter(Submission::getSolved)
                .map(Submission::getProblemKey)
                .collect(Collectors.toSet());
    }

    private List<String> getWeakTopics(
            User user
    ) {

        return topicAnalyticsRepository
                .findByUserId(user.getId())
                .stream()
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

    private int[] getGrowthZoneRange(
            User user
    ) {

        String band =
                ratingBandAnalyticsRepository
                        .findByUserId(user.getId())
                        .stream()
                        .filter(b ->
                                b.getAcceptanceRate() >= 40 &&
                                        b.getAcceptanceRate() <= 70
                        )
                        .max(
                                Comparator.comparing(
                                        this::bandOrder
                                )
                        )
                        .map(
                                RatingBandAnalytics::getRatingBand
                        )
                        .orElse("1200-1400");

        String[] parts =
                band.split("-");

        int min =
                Integer.parseInt(parts[0]);

        int max;

        if (parts.length == 2) {

            max =
                    Integer.parseInt(parts[1]);

        } else {

            max = Integer.MAX_VALUE;
        }

        return new int[]{
                Math.max(800, min - 100),
                max + 100
        };
    }

    private int bandOrder(
            RatingBandAnalytics band
    ) {

        return switch (band.getRatingBand()) {

            case "800-1000" -> 1;
            case "1000-1200" -> 2;
            case "1200-1400" -> 3;
            case "1400-1600" -> 4;
            case "1600-1800" -> 5;
            case "1800+" -> 6;

            default -> 0;
        };
    }

    public RecommendationResponse getTodayRecommendation() {

        User user = getCurrentUser();

        RecommendationResponse cachedRecommendation =
                redisRecommendationService.getRecommendation(
                        user.getId()
                );

        if (cachedRecommendation != null) {

            return cachedRecommendation;

        }

        RecommendationRequest request =
                RecommendationRequest.builder()
                        .build();

        RecommendationResponse recommendation =
                generateRecommendation(request);

        redisRecommendationService.saveRecommendation(
                user.getId(),
                recommendation
        );

        return recommendation;

    }

    public RecommendationResponse generateAnotherRecommendation() {

        User user = getCurrentUser();

        RecommendationRequest request =
                RecommendationRequest.builder()
                        .build();

        RecommendationResponse recommendation =
                generateRecommendation(request);

        redisRecommendationService.saveRecommendation(
                user.getId(),
                recommendation
        );

        return recommendation;

    }

}