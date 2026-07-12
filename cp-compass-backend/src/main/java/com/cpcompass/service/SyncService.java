package com.cpcompass.service;

import com.cpcompass.client.CodeforcesClient;
import com.cpcompass.dto.sync.*;
import com.cpcompass.entity.Contest;
import com.cpcompass.entity.Submission;
import com.cpcompass.entity.User;
import com.cpcompass.repository.ContestRepository;
import com.cpcompass.repository.SubmissionRepository;
import com.cpcompass.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class SyncService {

    private final UserRepository userRepository;
    private final ContestRepository contestRepository;
    private final SubmissionRepository submissionRepository;
    private final CodeforcesClient codeforcesClient;
    private final AnalyticsService analyticsService;

    public SyncResponse syncCurrentUser() {
        long start1 = System.currentTimeMillis();
        User user = getCurrentUser();

        checkCooldown(user);
        syncProfile(user);
        System.out.println("SYNC STARTED");
        long start2 = System.currentTimeMillis();
        int contestsSynced = syncContests(user);
        System.out.println("contestsSynced took: "
                + (System.currentTimeMillis() - start2) + " ms");


        long start3 = System.currentTimeMillis();
        int submissionsSynced = syncSubmissions(user);
        System.out.println("submissionsSynced took: "
                + (System.currentTimeMillis() - start3) + " ms");


        long start4 = System.currentTimeMillis();
        analyticsService.computeTopicAnalytics(user);
        System.out.println("computeTopicAnalytics took: "
                + (System.currentTimeMillis() - start4) + " ms");


        long start5 = System.currentTimeMillis();
        analyticsService.computeRatingBandAnalytics(user);
        System.out.println("computeRatingBandAnalytics took: "
                + (System.currentTimeMillis() - start5) + " ms");

        user.setLastSyncedAt(LocalDateTime.now());

        long start6 = System.currentTimeMillis();
        userRepository.save(user);
        System.out.println("DB save took: "
                + (System.currentTimeMillis() - start6) + " ms");

        System.out.println("total time took: "
                + (System.currentTimeMillis() - start1) + " ms");

        return new SyncResponse(
                "Sync completed successfully",
                contestsSynced,
                submissionsSynced
        );
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    private void checkCooldown(User user) {

        if (user.getLastSyncedAt() == null) {
            return;
        }

        Duration duration =
                Duration.between(
                        user.getLastSyncedAt(),
                        LocalDateTime.now()
                );

        if (duration.toMinutes() < 5) {

            throw new RuntimeException(
                    "Sync available after "
                            + (5 - duration.toMinutes())
                            + " minutes"
            );
        }
    }

    private void syncProfile(User user) {

//        System.out.println("Calling Codeforces user.info");
        CfUserInfoResponse response =
                codeforcesClient.getUserInfo(
                        user.getCodeforcesHandle()
                );

        if (response == null ||
                response.getResult() == null ||
                response.getResult().isEmpty()) {

            throw new RuntimeException(
                    "Failed to fetch Codeforces profile"
            );
        }

        CfUserDto profile =
                response.getResult().get(0);

        user.setCurrentRating(
                profile.getRating()
        );

        user.setMaxRating(
                profile.getMaxRating()
        );

        user.setRank(
                profile.getRank()
        );

        user.setMaxRank(
                profile.getMaxRank()
        );

        user.setContribution(
                profile.getContribution()
        );
    }

    private int syncContests(User user) {

        System.out.println("===== INSIDE syncContests =====");
        
        long start = System.currentTimeMillis();
        CfContestResponse response =
                codeforcesClient.getContestHistory(
                        user.getCodeforcesHandle()
                );

        System.out.println("Contest API took: "
                + (System.currentTimeMillis() - start) + " ms");

        if (response == null ||
                response.getResult() == null) {
            return 0;
        }

        Set<Long> existingContestIds =
                new HashSet<>(
                        contestRepository.findContestIdsByUserId(
                                user.getId()
                        )
                );

        List<Contest> newContests =
                new ArrayList<>();

        for (CfContestDto dto : response.getResult()) {

            if (existingContestIds.contains(dto.getContestId().longValue())) {
                break;
            }

            Contest contest =
                    Contest.builder()
                            .cfContestId(
                                    dto.getContestId().longValue()
                            )
                            .contestName(
                                    dto.getContestName()
                            )
                            .contestType(
                                    determineContestType(
                                            dto.getContestName()
                                    )
                            )
                            .rank(
                                    dto.getRank()
                            )
                            .oldRating(
                                    dto.getOldRating()
                            )
                            .newRating(
                                    dto.getNewRating()
                            )
                            .ratingChange(
                                    dto.getNewRating()
                                            - dto.getOldRating()
                            )
                            .contestTime(
                                    LocalDateTime.ofInstant(
                                            Instant.ofEpochSecond(
                                                    dto.getRatingUpdateTimeSeconds()
                                            ),
                                            ZoneOffset.UTC
                                    )
                            )
                            .user(user)
                            .build();

            newContests.add(contest);
        }

        if (newContests.isEmpty()) {
            return 0;
        }
        start = System.currentTimeMillis();
        contestRepository.saveAll(newContests);

        System.out.println("Contest DB save took: "
                + (System.currentTimeMillis() - start) + " ms");

        return newContests.size();
    }

    private int syncSubmissions(User user) {

        int from = 1;
        int count = 4000;

        long apiStart = System.currentTimeMillis();

        Set<Long> existingIds =
                new HashSet<>(
                        submissionRepository.findSubmissionIdsByUserId(
                                user.getId()
                        )
                );

        List<Submission> newSubmissions =
                new ArrayList<>();

        boolean syncComplete = false;

        while (!syncComplete) {

            CfSubmissionResponse response =
                    codeforcesClient.getSubmissions(
                            user.getCodeforcesHandle(),
                            from,
                            count
                    );

            if (response == null ||
                    response.getResult() == null ||
                    response.getResult().isEmpty()) {
                break;
            }

            List<CfSubmissionDto> cfSubmissions =
                    response.getResult();

            for (CfSubmissionDto dto : cfSubmissions) {

                if (dto.getProblem() == null ||
                        dto.getProblem().getContestId() == null) {
                    continue;
                }
                
                if (existingIds.contains(dto.getId())) {
                    syncComplete = true;
                    break;
                }

                Submission submission =
                        Submission.builder()
                                .cfSubmissionId(dto.getId())
                                .problemKey(
                                        dto.getProblem().getContestId()
                                                + "_"
                                                + dto.getProblem().getIndex()
                                )
                                .cfContestId(
                                        dto.getProblem().getContestId().longValue()
                                )
                                .problemIndex(
                                        dto.getProblem().getIndex()
                                )
                                .problemName(
                                        dto.getProblem().getName()
                                )
                                .problemRating(
                                        dto.getProblem().getRating()
                                )
                                .tags(
                                        dto.getProblem().getTags()
                                )
                                .rawVerdict(
                                        dto.getVerdict()
                                )
                                .solved(
                                        "OK".equals(dto.getVerdict())
                                )
                                .programmingLanguage(
                                        dto.getProgrammingLanguage()
                                )
                                .submissionTime(
                                        LocalDateTime.ofInstant(
                                                Instant.ofEpochSecond(
                                                        dto.getCreationTimeSeconds()
                                                ),
                                                ZoneOffset.UTC
                                        )
                                )
                                .user(user)
                                .build();

                newSubmissions.add(submission);
            }

            if (syncComplete) {
                break;
            }

            if (cfSubmissions.size() < count) {
                break;
            }

            from += count;
        }

        System.out.println("submission API took "
                + (System.currentTimeMillis() - apiStart) + " ms");

        if (newSubmissions.isEmpty()) {
            return 0;
        }

        long dbStart = System.currentTimeMillis();

        submissionRepository.saveAll(newSubmissions);

        System.out.println("syncSubmission DB save took "
                + (System.currentTimeMillis() - dbStart) + " ms");

        return newSubmissions.size();
    }
    private String determineContestType(
            String contestName
    ) {

        String name =
                contestName.toLowerCase();

        if (name.contains("educational")) {
            return "EDUCATIONAL";
        }

        if (name.contains("div. 2")) {
            return "DIV2";
        }

        if (name.contains("div. 3")) {
            return "DIV3";
        }

        if (name.contains("global")) {
            return "GLOBAL";
        }

        return "OTHER";
    }
}