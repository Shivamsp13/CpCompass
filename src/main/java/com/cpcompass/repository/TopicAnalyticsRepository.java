package com.cpcompass.repository;

import com.cpcompass.entity.TopicAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicAnalyticsRepository
        extends JpaRepository<TopicAnalytics, Long> {

    List<TopicAnalytics> findByUserId(
            Long userId
    );

    void deleteByUserId(
            Long userId
    );
}