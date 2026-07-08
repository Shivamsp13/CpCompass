package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CfContestResponse {

    private String status;

    private String comment;

    private List<CfContestDto> result;
}