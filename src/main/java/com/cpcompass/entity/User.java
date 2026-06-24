package com.cpcompass.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false,unique=true)
    private String codeforcesHandle;

    private Integer currentRating;

    private Integer maxRating;

    private String rank;

    private String maxRank;

    private Integer contribution;

    private LocalDateTime lastSyncedAt;

    @Column(nullable=false,updatable=false)
    private LocalDateTime createdAt;

    @Column(nullable=false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}