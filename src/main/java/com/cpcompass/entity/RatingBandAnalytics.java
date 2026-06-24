package com.cpcompass.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rating_band_analytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingBandAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ratingBand;

    @Column(nullable = false)
    private Integer attempts;

    @Column(nullable = false)
    private Integer accepted;

    @Column(nullable = false)
    private Double acceptanceRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}