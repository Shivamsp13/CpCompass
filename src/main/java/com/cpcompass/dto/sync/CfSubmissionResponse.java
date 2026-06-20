package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CfSubmissionResponse {

    private String status;

    private String comment;

    private List<CfSubmissionDto> result;
}