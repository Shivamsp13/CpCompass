package com.cpcompass.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic_analytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private Integer attempts;

    @Column(nullable = false)
    private Integer accepted;

    @Column(nullable = false)
    private Double acceptanceRate;

    @Column(nullable = false)
    private Double averageAttemptsBeforeAc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}