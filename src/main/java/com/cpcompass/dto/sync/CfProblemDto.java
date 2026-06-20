package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CfProblemDto {

    private Integer contestId;

    private String index;

    private String name;

    private Integer rating;

    private List<String> tags;
}