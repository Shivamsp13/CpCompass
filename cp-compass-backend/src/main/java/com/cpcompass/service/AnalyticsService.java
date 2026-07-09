package com.cpcompass.service;

import com.cpcompass.entity.RatingBandAnalytics;
import com.cpcompass.entity.Submission;
import com.cpcompass.entity.TopicAnalytics;
import com.cpcompass.entity.User;
import com.cpcompass.repository.RatingBandAnalyticsRepository;
import com.cpcompass.repository.SubmissionRepository;
import com.cpcompass.repository.TopicAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final SubmissionRepository submissionRepository;
    private final TopicAnalyticsRepository topicAnalyticsRepository;
    private final RatingBandAnalyticsRepository ratingBandAnalyticsRepository;

    public void computeTopicAnalytics(User user) {

        topicAnalyticsRepository.deleteByUserId(
                user.getId()
        );

        List<Submission> submissions =
                submissionRepository
                        .findByUserIdWithTagsOrderBySubmissionTimeDesc(
                                user.getId()
                        );

        Map<String, TopicStats> topicMap =
                new HashMap<>();

        // PASS 1
        for (Submission submission : submissions) {

            if (submission.getTags() == null) {
                continue;
            }

            for (String tag : submission.getTags()) {

                TopicStats stats =
                        topicMap.computeIfAbsent(
                                tag,
                                t -> new TopicStats()
                        );

                stats.attempts++;

                if (Boolean.TRUE.equals(
                        submission.getSolved()
                )) {

                    stats.accepted++;
                }
            }
        }

        // PASS 2
        Map<String, List<Submission>> problemMap =
                new HashMap<>();

        for (Submission submission : submissions) {

            problemMap
                    .computeIfAbsent(
                            submission.getProblemKey(),
                            k -> new ArrayList<>()
                    )
                    .add(submission);
        }

        for (List<Submission> problemSubmissions
                : problemMap.values()) {

            problemSubmissions.sort(
                    Comparator.comparing(
                            Submission::getSubmissionTime
                    )
            );

            int attemptsBeforeAc = 0;

            Submission acceptedSubmission = null;

            for (Submission submission
                    : problemSubmissions) {

                attemptsBeforeAc++;

                if (Boolean.TRUE.equals(
                        submission.getSolved()
                )) {

                    acceptedSubmission = submission;
                    break;
                }
            }

            if (acceptedSubmission == null) {
                continue;
            }

            if (acceptedSubmission.getTags() == null) {
                continue;
            }

            for (String tag
                    : acceptedSubmission.getTags()) {

                TopicStats stats =
                        topicMap.get(tag);

                if (stats == null) {
                    continue;
                }

                stats.totalAttemptsBeforeAc +=
                        attemptsBeforeAc;

                stats.solvedProblems++;
            }
        }

        List<TopicAnalytics> analytics =
                new ArrayList<>();

        for (Map.Entry<String, TopicStats> entry
                : topicMap.entrySet()) {

            String topic = entry.getKey();
            TopicStats stats = entry.getValue();

            double acceptanceRate = 0.0;

            if (stats.attempts > 0) {

                acceptanceRate =
                        (stats.accepted * 100.0)
                                / stats.attempts;
            }

            double averageAttemptsBeforeAc = 0.0;

            if (stats.solvedProblems > 0) {

                averageAttemptsBeforeAc =
                        (stats.totalAttemptsBeforeAc * 1.0)
                                / stats.solvedProblems;
            }

            TopicAnalytics topicAnalytics =
                    TopicAnalytics.builder()
                            .topic(topic)
                            .attempts(stats.attempts)
                            .accepted(stats.accepted)
                            .acceptanceRate(
                                    acceptanceRate
                            )
                            .averageAttemptsBeforeAc(
                                    averageAttemptsBeforeAc
                            )
                            .user(user)
                            .build();

            analytics.add(topicAnalytics);
        }

        topicAnalyticsRepository.saveAll(
                analytics
        );
    }

    public void computeRatingBandAnalytics(
            User user
    ) {

        ratingBandAnalyticsRepository
                .deleteByUserId(user.getId());

        List<Submission> submissions =
                submissionRepository
                        .findByUserIdOrderBySubmissionTimeDesc(
                                user.getId()
                        );

        Map<String, RatingBandStats> bandMap =
                new HashMap<>();

        for (Submission submission : submissions) {

            if (submission.getProblemRating()
                    == null) {

                continue;
            }

            String band =
                    getRatingBand(
                            submission.getProblemRating()
                    );

            RatingBandStats stats =
                    bandMap.computeIfAbsent(
                            band,
                            b -> new RatingBandStats()
                    );

            stats.attempts++;

            if (Boolean.TRUE.equals(
                    submission.getSolved()
            )) {

                stats.accepted++;
            }
        }

        List<RatingBandAnalytics> analytics =
                new ArrayList<>();

        for (Map.Entry<String, RatingBandStats> entry
                : bandMap.entrySet()) {

            String band = entry.getKey();

            RatingBandStats stats =
                    entry.getValue();

            double acceptanceRate =
                    (stats.accepted * 100.0)
                            / stats.attempts;

            RatingBandAnalytics row =
                    RatingBandAnalytics.builder()
                            .ratingBand(band)
                            .attempts(
                                    stats.attempts
                            )
                            .accepted(
                                    stats.accepted
                            )
                            .acceptanceRate(
                                    acceptanceRate
                            )
                            .user(user)
                            .build();

            analytics.add(row);
        }

        ratingBandAnalyticsRepository
                .saveAll(analytics);
    }

    private String getRatingBand(
            Integer rating
    ) {

        if (rating < 1000) {
            return "800-1000";
        }

        if (rating < 1200) {
            return "1000-1200";
        }

        if (rating < 1400) {
            return "1200-1400";
        }

        if (rating < 1600) {
            return "1400-1600";
        }

        if (rating < 1800) {
            return "1600-1800";
        }

        return "1800+";
    }

    private static class TopicStats {

        int attempts;

        int accepted;

        int totalAttemptsBeforeAc;

        int solvedProblems;
    }

    private static class RatingBandStats {

        int attempts;

        int accepted;
    }
}