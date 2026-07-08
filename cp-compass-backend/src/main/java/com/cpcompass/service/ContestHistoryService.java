package com.cpcompass.service;

import com.cpcompass.dto.contesthistory.ContestHistoryDto;
import com.cpcompass.entity.Contest;
import com.cpcompass.entity.Submission;
import com.cpcompass.entity.User;
import com.cpcompass.repository.ContestRepository;
import com.cpcompass.repository.SubmissionRepository;
import com.cpcompass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContestHistoryService {

    private final UserRepository userRepository;
    private final ContestRepository contestRepository;
    private final SubmissionRepository submissionRepository;

    public List<ContestHistoryDto> getContestHistory() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        List<Contest> contests =
                contestRepository
                        .findByUserId(user.getId());

        List<Submission> solvedSubmissions =
                submissionRepository
                        .findByUserAndSolvedTrue(user);

        Map<Long, String> solvedProblemsMap =
                solvedSubmissions.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Submission::getCfContestId,
                                        Collectors.mapping(
                                                Submission::getProblemIndex,
                                                Collectors.collectingAndThen(
                                                        Collectors.toList(),
                                                        this::formatSolvedProblems
                                                )
                                        )
                                )
                        );

        return contests.stream()
                .sorted(
                        Comparator.comparing(
                                Contest::getContestTime
                        ).reversed()
                )
                .map(
                        contest ->
                                ContestHistoryDto.builder()
                                        .contestId(
                                                contest.getCfContestId()
                                        )
                                        .rank(
                                                contest.getRank()
                                        )
                                        .contestUrl(
                                                "https://codeforces.com/contest/" + contest.getCfContestId()
                                        )
                                        .contestName(
                                                contest.getContestName()
                                        )
                                        .solvedProblems(
                                                solvedProblemsMap.getOrDefault(
                                                        contest.getCfContestId(),
                                                        "-"
                                                )
                                        )
                                        .contestDate(
                                                contest.getContestTime().toLocalDate()
                                        )
                                        .previousRating(
                                                contest.getOldRating()
                                        )
                                        .currentRating(
                                                contest.getNewRating()
                                        )
                                        .ratingChange(
                                                contest.getRatingChange()
                                        )
                                        .build()
                )
                .toList();
    }

    private String formatSolvedProblems(List<String> indices) {

        return indices.stream()
                .distinct()
                .sorted(this::compareProblemIndices)
                .collect(Collectors.joining());
    }

    private int compareProblemIndices(
            String first,
            String second
    ) {

        char firstLetter = first.charAt(0);
        char secondLetter = second.charAt(0);

        if (firstLetter != secondLetter) {
            return Character.compare(firstLetter, secondLetter);
        }

        String firstSuffix = first.substring(1);
        String secondSuffix = second.substring(1);

        if (firstSuffix.isEmpty() && secondSuffix.isEmpty()) {
            return 0;
        }

        if (firstSuffix.isEmpty()) {
            return -1;
        }

        if (secondSuffix.isEmpty()) {
            return 1;
        }

        return Integer.compare(
                Integer.parseInt(firstSuffix),
                Integer.parseInt(secondSuffix)
        );
    }
}