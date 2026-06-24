package com.cpcompass.dto.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendationRequest {

    private List<String> topics;

    private Integer minRating;

    private Integer maxRating;
}