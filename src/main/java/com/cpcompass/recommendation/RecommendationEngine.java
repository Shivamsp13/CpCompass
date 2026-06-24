package com.cpcompass.recommendation;

import com.cpcompass.dto.recommendation.RecommendationRequest;
import com.cpcompass.dto.sync.CfProblemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RecommendationEngine {

    private final Random random = new Random();

    public CfProblemDto generateRecommendation(
            List<CfProblemDto> problems,
            Set<String> solvedProblems,
            List<String> weakTopics,
            int minRating,
            int maxRating,
            RecommendationRequest request
    ) {

        List<CfProblemDto> filteredProblems =
                removeInvalidProblems(problems);

        filteredProblems =
                removeSolvedProblems(
                        filteredProblems,
                        solvedProblems
                );

        if (isCustomRecommendation(request)) {

            filteredProblems =
                    applyCustomFilters(
                            filteredProblems,
                            request
                    );

            if (!filteredProblems.isEmpty()) {

                return pickRandomProblem(filteredProblems);
            }

            int fallbackMin =
                    request.getMinRating() == null
                            ? 800
                            : Math.max(800, request.getMinRating() - 100);

            int fallbackMax =
                    request.getMaxRating() == null
                            ? 3500
                            : request.getMaxRating() + 100;

            filteredProblems =
                    filteredProblems =
                            removeSolvedProblems(
                                    removeInvalidProblems(problems),
                                    solvedProblems
                            );

            filteredProblems =
                    filteredProblems.stream()
                            .filter(problem ->
                                    problem.getRating() >= fallbackMin
                            )
                            .filter(problem ->
                                    problem.getRating() <= fallbackMax
                            )
                            .toList();

            if (!filteredProblems.isEmpty()) {

                return pickRandomProblem(filteredProblems);
            }

            filteredProblems =
                    removeSolvedProblems(
                            removeInvalidProblems(problems),
                            solvedProblems
                    );

            return pickRandomProblem(filteredProblems);
        }

        filteredProblems =
                applySmartRecommendation(
                        filteredProblems,
                        weakTopics,
                        minRating,
                        maxRating
                );

        return pickRandomProblem(filteredProblems);
    }

    private boolean isCustomRecommendation(
            RecommendationRequest request
    ) {

        return (request.getTopics() != null &&
                !request.getTopics().isEmpty())
                ||
                request.getMinRating() != null
                ||
                request.getMaxRating() != null;
    }

    private List<CfProblemDto> applyCustomFilters(
            List<CfProblemDto> problems,
            RecommendationRequest request
    ) {

        if (request.getMinRating() != null) {

            problems =
                    problems.stream()
                            .filter(problem ->
                                    problem.getRating()
                                            >= request.getMinRating()
                            )
                            .toList();
        }

        if (request.getMaxRating() != null) {

            problems =
                    problems.stream()
                            .filter(problem ->
                                    problem.getRating()
                                            <= request.getMaxRating()
                            )
                            .toList();
        }

        if (request.getTopics() != null &&
                !request.getTopics().isEmpty()) {

            List<String> selectedTopics =
                    request.getTopics()
                            .stream()
                            .map(String::toLowerCase)
                            .toList();

            problems =
                    problems.stream()
                            .filter(problem ->
                                    problem.getTags()
                                            .stream()
                                            .map(String::toLowerCase)
                                            .anyMatch(
                                                    selectedTopics::contains
                                            )
                            )
                            .toList();
        }

        return problems;
    }

    private List<CfProblemDto> applySmartRecommendation(
            List<CfProblemDto> problems,
            List<String> weakTopics,
            int minRating,
            int maxRating
    ) {

        List<CfProblemDto> weakGrowthProblems =
                problems.stream()
                        .filter(problem ->
                                problem.getRating() >= minRating
                        )
                        .filter(problem ->
                                problem.getRating() <= maxRating
                        )
                        .filter(problem ->
                                problem.getTags()
                                        .stream()
                                        .anyMatch(
                                                weakTopics::contains
                                        )
                        )
                        .toList();

        if (!weakGrowthProblems.isEmpty()) {

            return weakGrowthProblems;
        }

        List<CfProblemDto> growthProblems =
                problems.stream()
                        .filter(problem ->
                                problem.getRating() >= minRating
                        )
                        .filter(problem ->
                                problem.getRating() <= maxRating
                        )
                        .toList();

        if (!growthProblems.isEmpty()) {

            return growthProblems;
        }

        List<CfProblemDto> nearbyProblems =
                problems.stream()
                        .filter(problem ->
                                problem.getRating() >= Math.max(800, minRating - 100)
                        )
                        .filter(problem ->
                                problem.getRating() <= maxRating + 100)
                        .toList();

        if (!nearbyProblems.isEmpty()) {

            return nearbyProblems;
        }

        return problems;
    }

    private List<CfProblemDto> removeSolvedProblems(
            List<CfProblemDto> problems,
            Set<String> solvedProblems
    ) {

        return problems.stream()
                .filter(problem ->
                        !solvedProblems.contains(
                                buildProblemKey(problem)
                        )
                )
                .toList();
    }

    private List<CfProblemDto> removeInvalidProblems(
            List<CfProblemDto> problems
    ) {

        return problems.stream()
                .filter(problem ->
                        problem.getContestId() != null
                )
                .filter(problem ->
                        problem.getRating() != null
                )
                .filter(problem ->
                        problem.getTags() != null
                )
                .filter(problem ->
                        !problem.getTags().isEmpty()
                )
                .toList();
    }

    private CfProblemDto pickRandomProblem(
            List<CfProblemDto> problems
    ) {

        return problems.get(
                random.nextInt(
                        problems.size()
                )
        );
    }

    private String buildProblemKey(
            CfProblemDto problem
    ) {

        return problem.getContestId()
                + "_"
                + problem.getIndex();
    }
}