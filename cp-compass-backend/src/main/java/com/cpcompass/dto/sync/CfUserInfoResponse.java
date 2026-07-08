package com.cpcompass.dto.sync;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CfUserInfoResponse {

    private String status;

    private String comment;

    private List<CfUserDto> result;
}