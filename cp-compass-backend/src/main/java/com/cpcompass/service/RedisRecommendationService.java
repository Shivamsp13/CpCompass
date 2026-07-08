package com.cpcompass.service;

import com.cpcompass.dto.recommendation.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RedisRecommendationService {

    private static final String KEY_PREFIX =
            "cpcompass:recommendation:";

    private final RedisTemplate<String, Object> redisTemplate;

    public RecommendationResponse getRecommendation(
            Long userId
    ) {

        return (RecommendationResponse)
                redisTemplate.opsForValue()
                        .get(KEY_PREFIX + userId);

    }

    public void saveRecommendation(
            Long userId,
            RecommendationResponse recommendation
    ) {

        redisTemplate.opsForValue().set(

                KEY_PREFIX + userId,

                recommendation,

                getDurationUntilMidnight()

        );

    }

    public void deleteRecommendation(
            Long userId
    ) {

        redisTemplate.delete(
                KEY_PREFIX + userId
        );

    }

    private Duration getDurationUntilMidnight() {

        LocalDateTime now =
                LocalDateTime.now();

        LocalDateTime midnight =
                now.toLocalDate()
                        .plusDays(1)
                        .atStartOfDay();

        return Duration.between(
                now,
                midnight
        );

    }

}