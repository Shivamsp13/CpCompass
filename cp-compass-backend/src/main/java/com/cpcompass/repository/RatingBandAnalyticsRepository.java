package com.cpcompass.repository;

import com.cpcompass.entity.RatingBandAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingBandAnalyticsRepository
        extends JpaRepository<RatingBandAnalytics, Long> {

    List<RatingBandAnalytics> findByUserId(
            Long userId
    );

    void deleteByUserId(
            Long userId
    );
}