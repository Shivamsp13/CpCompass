package com.cpcompass.dto.sync;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SyncResponse {

    private String message;

    private int contestsSynced;

    private int submissionsSynced;
}