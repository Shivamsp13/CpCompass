package com.cpcompass.service;

import com.cpcompass.dto.progressreview.ActivitySummaryResponse;
import com.cpcompass.dto.progressreview.ProgressReviewResponse;
import com.cpcompass.dto.progressreview.RatingDistributionDto;
import com.cpcompass.entity.Submission;
import com.cpcompass.entity.User;
import com.cpcompass.repository.ContestRepository;
import com.cpcompass.repository.SubmissionRepository;
import com.cpcompass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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

            days = 30;

        }

        User user = getCurrentUser();

        LocalDate today = LocalDate.now();

        LocalDateTime startTime =
                today.minusDays(days - 1).atStartOfDay();

        LocalDateTime endTime =
                today.atTime(LocalTime.MAX);

        return ProgressReviewResponse
                .builder()
                .activitySummary(
                        buildActivitySummary(
                                user,
                                startTime,
                                endTime
                        )
                )
                .build();

    }

    private ActivitySummaryResponse buildActivitySummary(
            User user,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        Long problemsSolved =
                submissionRepository.countSolvedProblems(
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

        Long ratingDelta =
                contestRepository.findRatingChange(
                        user,
                        startTime,
                        endTime
                );

        List<Submission> solvedSubmissions =
                submissionRepository.findSolvedSubmissionsBetween(
                        user,
                        startTime,
                        endTime
                );

        List<Submission> allSolvedSubmissions =
                submissionRepository
                        .findByUserAndSolvedTrueOrderBySubmissionTimeAsc(
                                user
                        );

        return ActivitySummaryResponse
                .builder()
                .problemsSolved(
                        problemsSolved
                )
                .contestsParticipated(
                        contestsParticipated
                )
                .ratingChange(
                        ratingDelta.intValue()
                )
                .currentStreak(
                        calculateCurrentStreak(
                                allSolvedSubmissions
                        )
                )
                .longestStreak(
                        calculateLongestStreak(
                                allSolvedSubmissions
                        )
                )
                .ratingDistribution(
                        buildRatingDistribution(
                                solvedSubmissions
                        )
                )
                .build();

    }

    private List<RatingDistributionDto> buildRatingDistribution(
            List<Submission> solvedSubmissions
    ) {

        Map<Integer, Integer> distribution =
                new TreeMap<>();

        for (int rating = 800; rating <= 3500; rating += 100) {

            distribution.put(
                    rating,
                    0
            );

        }

        Set<String> countedProblems =
                new HashSet<>();

        for (Submission submission : solvedSubmissions) {

            if (submission.getProblemRating() == null) {

                continue;

            }

            if (!countedProblems.add(
                    submission.getProblemKey()
            )) {

                continue;

            }

            int bucket =
                    (submission.getProblemRating() / 100) * 100;

            if (bucket < 800 || bucket > 3500) {

                continue;

            }

            distribution.put(
                    bucket,
                    distribution.get(bucket) + 1
            );

        }

        List<RatingDistributionDto> result =
                new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry :
                distribution.entrySet()) {

            result.add(

                    RatingDistributionDto
                            .builder()
                            .rating(
                                    entry.getKey()
                            )
                            .solved(
                                    entry.getValue()
                            )
                            .build()

            );

        }

        return result;

    }

    private Integer calculateCurrentStreak(
            List<Submission> solvedSubmissions
    ) {

        if (solvedSubmissions.isEmpty()) {

            return 0;

        }

        Set<LocalDate> solvedDays =
                new HashSet<>();

        for (Submission submission : solvedSubmissions) {

            solvedDays.add(
                    submission
                            .getSubmissionTime()
                            .toLocalDate()
            );

        }

        LocalDate today =
                LocalDate.now();

        LocalDate currentDay;

        if (solvedDays.contains(today)) {

            currentDay = today;

        }

        else if (solvedDays.contains(
                today.minusDays(1)
        )) {

            currentDay = today.minusDays(1);

        }

        else {

            return 0;

        }

        int streak = 0;

        while (solvedDays.contains(currentDay)) {

            streak++;

            currentDay =
                    currentDay.minusDays(1);

        }

        return streak;

    }
    private Integer calculateLongestStreak(
            List<Submission> solvedSubmissions
    ) {

        if (solvedSubmissions.isEmpty()) {

            return 0;

        }

        Set<LocalDate> solvedDays =
                new TreeSet<>();

        for (Submission submission : solvedSubmissions) {

            solvedDays.add(
                    submission
                            .getSubmissionTime()
                            .toLocalDate()
            );

        }

        int longest = 1;

        int current = 1;

        LocalDate previous = null;

        for (LocalDate date : solvedDays) {

            if (previous != null &&
                    previous.plusDays(1).equals(date)) {

                current++;

            }

            else if (previous != null) {

                current = 1;

            }

            longest = Math.max(
                    longest,
                    current
            );

            previous = date;

        }

        return longest;

    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new RuntimeException(
                                        "User not found"
                                )
                );

    }

}