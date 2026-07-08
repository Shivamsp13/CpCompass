package com.cpcompass.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long cfContestId;

    @Column(nullable = false)
    private String contestName;

    @Column(nullable = false)
    private String contestType;

    @Column(nullable = false)
    private Integer rank;

    @Column(nullable = false)
    private Integer oldRating;

    @Column(nullable = false)
    private Integer newRating;

    @Column(nullable = false)
    private Integer ratingChange;

    @Column(nullable = false)
    private LocalDateTime contestTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}