package com.cpcompass.dto.progressreview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricComparisonDto {

    private Double currentValue;

    private Double previousValue;

    private Double change;

}