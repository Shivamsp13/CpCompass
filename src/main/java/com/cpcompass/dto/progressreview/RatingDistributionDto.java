package com.cpcompass.dto.progressreview;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDistributionDto {

    private Integer rating;

    private Integer solved;

}
