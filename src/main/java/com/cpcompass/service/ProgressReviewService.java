package com.cpcompass.service;

import com.cpcompass.dto.progressreview.ActivitySummaryResponse;
import com.cpcompass.dto.progressreview.MetricComparisonDto;
import com.cpcompass.dto.progressreview.ProgressComparisonResponse;
import com.cpcompass.dto.progressreview.ProgressReviewResponse;
import com.cpcompass.entity.Submission;
import com.cpcompass.entity.User;
import com.cpcompass.repository.ContestRepository;
import com.cpcompass.repository.SubmissionRepository;
import com.cpcompass.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProgressReviewService {

    private final SubmissionRepository submissionRepository;

    private final ContestRepository contestRepository;

    private final UserRepository userRepository;

    public ProgressReviewResponse getProgressReview(
            Integer days
    ) {

        if (days == null || days <= 0) {

            throw new RuntimeException(
                    "Days must be greater than zero."
            );

        }

        User user =
                getCurrentUser();

        LocalDateTime currentEnd =
                LocalDateTime.now();

        LocalDateTime currentStart =
                currentEnd.minusDays(days);

        LocalDateTime previousEnd =
                currentStart;

        LocalDateTime previousStart =
                previousEnd.minusDays(days);

        ProgressMetrics currentMetrics =
                calculateMetrics(
                        user,
                        currentStart,
                        currentEnd,
                        days
                );

        ProgressMetrics previousMetrics =
                calculateMetrics(
                        user,
                        previousStart,
                        previousEnd,
                        days
                );

        return ProgressReviewResponse
                .builder()
                .activitySummary(
                        buildActivitySummary(
                                currentMetrics
                        )
                )
                .progressComparison(
                        buildProgressComparison(
                                currentMetrics,
                                previousMetrics
                        )
                )
                .build();

    }

    private ProgressMetrics calculateMetrics(
            User user,
            LocalDateTime startTime,
            LocalDateTime endTime,
            Integer days
    ) {

        Long problemsSolved =
                submissionRepository.countSolvedProblems(
                        user,
                        startTime,
                        endTime
                );

        Double averageSolvedRating =
                submissionRepository.findAverageSolvedRating(
                        user,
                        startTime,
                        endTime
                );

        Integer highestSolvedRating =
                submissionRepository.findHighestSolvedRating(
                        user,
                        startTime,
                        endTime
                );

        Long contestsParticipated =
                contestRepository.countContestsParticipated(
                        user,
                        startTime,
                        endTime
                );

        Long ratingChange =
                contestRepository.findRatingChange(
                        user,
                        startTime,
                        endTime
                );

        Double averageProblemsPerDay =
                problemsSolved.doubleValue()
                        / days;

        List<Submission> solvedSubmissions =
                submissionRepository.findSolvedSubmissionsBetween(
                        user,
                        startTime,
                        endTime
                );

        Set<String> uniqueTopics =
                new HashSet<>();

        for (Submission submission : solvedSubmissions) {

            if (submission.getTags() != null) {

                uniqueTopics.addAll(
                        submission.getTags()
                );

            }

        }

        return ProgressMetrics
                .builder()
                .problemsSolved(
                        problemsSolved
                )
                .averageSolvedRating(
                        averageSolvedRating
                )
                .highestSolvedRating(
                        highestSolvedRating
                )
                .contestsParticipated(
                        contestsParticipated
                )
                .ratingChange(
                        ratingChange
                )
                .averageProblemsPerDay(
                        averageProblemsPerDay
                )
                .uniqueTopicsSolved(
                        (long) uniqueTopics.size()
                )
                .build();

    }
    private ActivitySummaryResponse buildActivitySummary(
            ProgressMetrics metrics
    ) {

        return ActivitySummaryResponse
                .builder()
                .problemsSolved(
                        metrics.getProblemsSolved()
                )
                .averageSolvedRating(
                        metrics.getAverageSolvedRating()
                )
                .highestSolvedRating(
                        metrics.getHighestSolvedRating()
                )
                .contestsParticipated(
                        metrics.getContestsParticipated()
                )
                .averageProblemsPerDay(
                        metrics.getAverageProblemsPerDay()
                )
                .build();

    }

    private ProgressComparisonResponse buildProgressComparison(
            ProgressMetrics current,
            ProgressMetrics previous
    ) {

        return ProgressComparisonResponse
                .builder()
                .problemsSolved(
                        buildComparison(
                                current.getProblemsSolved().doubleValue(),
                                previous.getProblemsSolved().doubleValue()
                        )
                )
                .averageSolvedRating(
                        buildComparison(
                                current.getAverageSolvedRating(),
                                previous.getAverageSolvedRating()
                        )
                )
                .highestSolvedRating(
                        buildComparison(
                                current.getHighestSolvedRating() == null
                                        ? null
                                        : current.getHighestSolvedRating().doubleValue(),
                                previous.getHighestSolvedRating() == null
                                        ? null
                                        : previous.getHighestSolvedRating().doubleValue()
                        )
                )
                .ratingChange(
                        buildComparison(
                                current.getRatingChange().doubleValue(),
                                previous.getRatingChange().doubleValue()
                        )
                )
                .contestsParticipated(
                        buildComparison(
                                current.getContestsParticipated().doubleValue(),
                                previous.getContestsParticipated().doubleValue()
                        )
                )
                .averageProblemsPerDay(
                        buildComparison(
                                current.getAverageProblemsPerDay(),
                                previous.getAverageProblemsPerDay()
                        )
                )
                .uniqueTopicsSolved(
                        buildComparison(
                                current.getUniqueTopicsSolved().doubleValue(),
                                previous.getUniqueTopicsSolved().doubleValue()
                        )
                )
                .build();

    }

    private MetricComparisonDto buildComparison(
            Double currentValue,
            Double previousValue
    ) {

        Double change = null;

        if (
                currentValue != null
                        &&
                        previousValue != null
        ) {

            change =
                    currentValue
                            -
                            previousValue;

        }

        return MetricComparisonDto
                .builder()
                .currentValue(
                        currentValue
                )
                .previousValue(
                        previousValue
                )
                .change(
                        change
                )
                .build();

    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(
                        email
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class ProgressMetrics {

        private Long problemsSolved;

        private Double averageSolvedRating;

        private Integer highestSolvedRating;

        private Long contestsParticipated;

        private Long ratingChange;

        private Double averageProblemsPerDay;

        private Long uniqueTopicsSolved;

    }

}