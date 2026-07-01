package com.cpcompass.dto.progressreview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySummaryResponse {

    private Long problemsSolved;

    private Double averageSolvedRating;

    private Integer highestSolvedRating;

    private Long contestsParticipated;

    private Double averageProblemsPerDay;

}