package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CfContestDto {

    private Integer contestId;

    private String contestName;

    private Integer rank;

    private Long ratingUpdateTimeSeconds;

    private Integer oldRating;

    private Integer newRating;
}