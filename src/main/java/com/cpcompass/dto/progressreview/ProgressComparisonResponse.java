package com.cpcompass.dto.progressreview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressComparisonResponse {

    private MetricComparisonDto problemsSolved;

    private MetricComparisonDto averageSolvedRating;

    private MetricComparisonDto highestSolvedRating;

    private MetricComparisonDto ratingChange;

    private MetricComparisonDto contestsParticipated;

    private MetricComparisonDto averageProblemsPerDay;

    private MetricComparisonDto uniqueTopicsSolved;

}