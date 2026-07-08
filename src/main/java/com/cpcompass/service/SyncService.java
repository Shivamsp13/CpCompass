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

        User user = getCurrentUser();

        checkCooldown(user);

        syncProfile(user);

        int contestsSynced = syncContests(user);

        int submissionsSynced = syncSubmissions(user);

        analyticsService.computeTopicAnalytics(user);
        analyticsService.computeRatingBandAnalytics(user);

        user.setLastSyncedAt(LocalDateTime.now());

        userRepository.save(user);

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

        contestRepository.deleteByUserId(
                user.getId()
        );
        contestRepository.flush();

        CfContestResponse response =
                codeforcesClient.getContestHistory(
                        user.getCodeforcesHandle()
                );

        if (response == null ||
                response.getResult() == null) {

            return 0;
        }

        List<Contest> contests =
                new ArrayList<>();

        for (CfContestDto dto : response.getResult()) {

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

            contests.add(contest);
        }

        contestRepository.saveAll(contests);

        return contests.size();
    }

    private int syncSubmissions(User user) {

        submissionRepository.deleteByUserId(
                user.getId()
        );
        submissionRepository.flush();

        CfSubmissionResponse response =
                codeforcesClient.getSubmissions(
                        user.getCodeforcesHandle()
                );

        if (response == null ||
                response.getResult() == null) {

            return 0;
        }

        List<CfSubmissionDto> cfSubmissions =
                response.getResult();

        int limit =
                Math.min(
                        2000,
                        cfSubmissions.size()
                );

        List<Submission> submissions =
                new ArrayList<>();

        for (int i = 0; i < limit; i++) {

            CfSubmissionDto dto =
                    cfSubmissions.get(i);

            if (dto.getProblem() == null ||
                    dto.getProblem().getContestId() == null) {

                continue;
            }

            Submission submission =
                    Submission.builder()
                            .cfSubmissionId(
                                    dto.getId()
                            )
                            .problemKey(
                                    dto.getProblem()
                                            .getContestId()
                                            + "_"
                                            + dto.getProblem()
                                            .getIndex()
                            )
                            .cfContestId(
                                    dto.getProblem()
                                            .getContestId()
                                            .longValue()
                            )
                            .problemIndex(
                                    dto.getProblem()
                                            .getIndex()
                            )
                            .problemName(
                                    dto.getProblem()
                                            .getName()
                            )
                            .problemRating(
                                    dto.getProblem()
                                            .getRating()
                            )
                            .tags(
                                    dto.getProblem()
                                            .getTags()
                            )
                            .rawVerdict(
                                    dto.getVerdict()
                            )
                            .solved(
                                    "OK".equals(
                                            dto.getVerdict()
                                    )
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

            submissions.add(submission);
        }

        Set<Long> ids = new HashSet<>();

        for (Submission s : submissions) {

            if (!ids.add(s.getCfSubmissionId())) {

                System.out.println(
                        "DUPLICATE FOUND: "
                                + s.getCfSubmissionId()
                );
            }
        }

        submissionRepository.saveAll(
                submissions
        );

        return submissions.size();
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