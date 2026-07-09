package com.cpcompass.repository;

import com.cpcompass.entity.Contest;
import com.cpcompass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    List<Contest> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    @Query("""
            SELECT COUNT(c)
            FROM Contest c
            WHERE c.user = :user
            AND c.contestTime BETWEEN :startTime AND :endTime
            """)
    Long countContestsParticipated(
            @Param("user") User user,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
       SELECT c.cfContestId
       FROM Contest c
       WHERE c.user.id = :userId
       """)
    List<Long> findContestIdsByUserId(Long userId);

    @Query("""
            SELECT COALESCE(SUM(c.ratingChange), 0)
            FROM Contest c
            WHERE c.user = :user
            AND c.contestTime BETWEEN :startTime AND :endTime
            """)
    Long findRatingChange(
            @Param("user") User user,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}