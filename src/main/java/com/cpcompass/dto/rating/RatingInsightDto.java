package com.cpcompass.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RatingInsightDto {

    private String ratingBand;

    private Integer attempts;

    private Integer accepted;

    private Double acceptanceRate;
}