package com.cpcompass.dto.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TopicInsightDto {

    private String topic;

    private Integer attempts;

    private Integer accepted;

    private Double acceptanceRate;

    private Double averageAttemptsBeforeAc;
}