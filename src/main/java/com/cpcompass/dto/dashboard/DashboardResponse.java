package com.cpcompass.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Integer currentRating;

    private Integer maxRating;

    private List<String> strongTopics;

    private List<String> weakTopics;

    private List<String> strongRatingBands;

    private String growthZone;

    private List<String> weakRatingBands;

    private Long totalContests;

    private Long totalSubmissions;

    private Long totalSolved;
}