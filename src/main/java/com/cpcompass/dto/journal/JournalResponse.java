package com.cpcompass.dto.journal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JournalResponse {

    private Long id;

    private String problemName;

    private String problemUrl;

    private String topic;

    private String whatILearned;

    private String mistakes;

    private String observation;

    private LocalDateTime createdAt;
}