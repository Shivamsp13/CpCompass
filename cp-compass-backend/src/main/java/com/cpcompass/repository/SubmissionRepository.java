package com.cpcompass.repository;

import com.cpcompass.entity.Submission;
import com.cpcompass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {

    List<Submission> findByUserIdOrderBySubmissionTimeDesc(
            Long userId
    );

    boolean existsByCfSubmissionId(Long cfSubmissionId);

    List<Submission> findByUserAndSolvedTrue(User user);
    long countByUserIdAndSolvedTrueAndSubmissionTimeAfter(
            Long userId,
            LocalDateTime submissionTime
    );
    List<Submission> findByUserOrderBySubmissionTimeAsc(User user);
    List<Submission> findByUserAndSolvedTrueOrderBySubmissionTimeAsc(User user);


    void deleteByUserId(Long userId);

    @Query("""
            SELECT COUNT(DISTINCT s.problemKey)
            FROM Submission s
            WHERE s.user = :user
            AND s.solved = true
            AND s.submissionTime BETWEEN :startTime AND :endTime
            """)
    Long countSolvedProblems(
            @Param("user") User user,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
    @Query("""
       SELECT DISTINCT s
       FROM Submission s
       LEFT JOIN FETCH s.tags
       WHERE s.user.id = :userId
       ORDER BY s.submissionTime DESC
       """)
    List<Submission> findByUserIdWithTagsOrderBySubmissionTimeDesc(
            @Param("userId") Long userId
    );

    @Query("""
       SELECT s.cfSubmissionId
       FROM Submission s
       WHERE s.user.id = :userId
       """)
    List<Long> findSubmissionIdsByUserId(Long userId);


    @Query("""
            SELECT AVG(s.problemRating)
            FROM Submission s
            WHERE s.user = :user
            AND s.solved = true
            AND s.problemRating IS NOT NULL
            AND s.submissionTime BETWEEN :startTime AND :endTime
            """)
    Double findAverageSolvedRating(
            @Param("user") User user,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
            SELECT MAX(s.problemRating)
            FROM Submission s
            WHERE s.user = :user
            AND s.solved = true
            AND s.problemRating IS NOT NULL
            AND s.submissionTime BETWEEN :startTime AND :endTime
            """)
    Integer findHighestSolvedRating(
            @Param("user") User user,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("""
            SELECT s
            FROM Submission s
            WHERE s.user = :user
            AND s.solved = true
            AND s.submissionTime BETWEEN :startTime AND :endTime
            """)
    List<Submission> findSolvedSubmissionsBetween(
            @Param("user") User user,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );


    @Query("""
        SELECT s
        FROM Submission s
        WHERE s.user = :user
        AND s.solved = true
        AND s.submissionTime BETWEEN :startTime AND :endTime
        """)
    List<Submission> findSolvedProblemsBetween(
            @Param("user") User user,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

}