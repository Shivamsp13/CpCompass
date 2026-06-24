package com.cpcompass.dto.journal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJournalRequest {

    private String problemName;

    private String problemUrl;

    private String topic;

    private String whatILearned;

    private String mistakes;

    private String observation;
}