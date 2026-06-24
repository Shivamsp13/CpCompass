package com.cpcompass.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RatingInsightsResponse {

    private List<RatingInsightDto> ratingBands;
}