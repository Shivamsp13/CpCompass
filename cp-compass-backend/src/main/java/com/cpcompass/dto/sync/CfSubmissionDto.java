package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CfSubmissionDto {

    private Long id;

    private String verdict;

    private String programmingLanguage;

    private Long creationTimeSeconds;

    private CfProblemDto problem;
}