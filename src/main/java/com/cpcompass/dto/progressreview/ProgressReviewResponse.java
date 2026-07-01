package com.cpcompass.dto.progressreview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressReviewResponse {

    private ActivitySummaryResponse activitySummary;

    private ProgressComparisonResponse progressComparison;

}