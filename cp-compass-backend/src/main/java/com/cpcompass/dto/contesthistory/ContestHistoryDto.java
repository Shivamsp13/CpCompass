package com.cpcompass.dto.contesthistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestHistoryDto {

    private Long contestId;

    private String contestName;

    private String solvedProblems;

    private LocalDate contestDate;

    private Integer previousRating;

    private Integer currentRating;

    private Integer ratingChange;

    private String contestUrl;

    private Integer rank;
}
