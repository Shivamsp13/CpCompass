package com.cpcompass.dto.progressreview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivitySummaryResponse {

    private Long problemsSolved;

    private Long contestsParticipated;

    private Integer ratingChange;

    private Integer currentStreak;

    private Integer longestStreak;

    private List<RatingDistributionDto> ratingDistribution;

}