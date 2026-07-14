package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CfContestListResponse {

    private String status;

    private String comment;

    private List<CfContestListDto> result;
}
