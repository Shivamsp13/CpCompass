package com.cpcompass.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long cfSubmissionId;

    @Column(nullable = false)
    private String problemKey;

    @Column(nullable = false)
    private Long cfContestId;

    @Column(nullable = false)
    private String problemIndex;

    @Column(nullable = false)
    private String problemName;

    private Integer problemRating;

    @ElementCollection
    @CollectionTable(
            name = "submission_tags",
            joinColumns = @JoinColumn(name = "submission_id")
    )
    @Column(name = "tag")
    private List<String> tags;

    @Column(nullable = false)
    private String rawVerdict;

    @Column(nullable = false)
    private Boolean solved;

    @Column(nullable = false)
    private String programmingLanguage;

    @Column(nullable = false)
    private LocalDateTime submissionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}