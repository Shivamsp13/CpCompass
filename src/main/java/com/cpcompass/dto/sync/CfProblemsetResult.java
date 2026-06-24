package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CfProblemsetResult {

    private List<CfProblemDto> problems;
}