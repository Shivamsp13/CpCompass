package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CfContestListDto {

    private Integer id;

    private String name;

    private String type;

    private String phase;

    private Boolean frozen;

    private Integer durationSeconds;

    private Long startTimeSeconds;
}
