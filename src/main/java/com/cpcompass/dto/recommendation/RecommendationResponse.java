package com.cpcompass.dto.recommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RecommendationResponse {

    private Integer contestId;

    private String problemIndex;

    private String problemName;

    private Integer rating;

    private List<String> tags;

    private String url;
}