package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CfUserDto {

    private String handle;

    private Integer rating;

    private Integer maxRating;

    private String rank;

    private String maxRank;

    private Integer contribution;
}