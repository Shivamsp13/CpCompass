package com.cpcompass.dto.recommendation;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationResponse {

    private Integer contestId;

    private String problemIndex;

    private String problemName;

    private Integer rating;

    private List<String> tags;

    private String url;
}