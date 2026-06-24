package com.cpcompass.service;

import com.cpcompass.dto.rating.RatingInsightDto;
import com.cpcompass.dto.rating.RatingInsightsResponse;
import com.cpcompass.entity.RatingBandAnalytics;
import com.cpcompass.entity.User;
import com.cpcompass.repository.RatingBandAnalyticsRepository;
import com.cpcompass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingInsightsService {

    private final UserRepository userRepository;
    private final RatingBandAnalyticsRepository ratingBandAnalyticsRepository;

    public RatingInsightsResponse getRatingInsights() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        List<RatingInsightDto> ratingBands =
                ratingBandAnalyticsRepository
                        .findByUserId(user.getId())
                        .stream()
                        .sorted(
                                Comparator.comparing(
                                        this::bandOrder
                                )
                        )
                        .map(
                                band ->
                                        RatingInsightDto.builder()
                                                .ratingBand(
                                                        band.getRatingBand()
                                                )
                                                .attempts(
                                                        band.getAttempts()
                                                )
                                                .accepted(
                                                        band.getAccepted()
                                                )
                                                .acceptanceRate(
                                                        band.getAcceptanceRate()
                                                )
                                                .build()
                        )
                        .toList();

        return RatingInsightsResponse.builder()
                .ratingBands(ratingBands)
                .build();
    }

    private int bandOrder(
            RatingBandAnalytics band
    ) {

        return switch (
                band.getRatingBand()
                ) {

            case "800-1000" -> 1;

            case "1000-1200" -> 2;

            case "1200-1400" -> 3;

            case "1400-1600" -> 4;

            case "1600-1800" -> 5;

            case "1800+" -> 6;

            default -> 999;
        };
    }
}