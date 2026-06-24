package com.cpcompass.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "journal_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String problemName;

    private String problemUrl;

    private String topic;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String whatILearned;

    @Column(columnDefinition = "TEXT")
    private String mistakes;

    @Column(columnDefinition = "TEXT")
    private String observation;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {

        createdAt =
                LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
}