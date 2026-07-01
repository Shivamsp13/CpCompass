package com.cpcompass.dto.recommendation;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {

    private List<String> topics;

    private Integer minRating;

    private Integer maxRating;
}