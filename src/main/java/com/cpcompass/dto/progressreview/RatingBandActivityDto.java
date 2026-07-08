package com.cpcompass.dto.progressreview;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingBandActivityDto {

    private String ratingBand;

    private Integer solved;

}