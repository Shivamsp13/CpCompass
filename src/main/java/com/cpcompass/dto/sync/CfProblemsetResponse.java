package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CfProblemsetResponse {

    private String status;

    private CfProblemsetResult result;
}