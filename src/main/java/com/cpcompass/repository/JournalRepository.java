package com.cpcompass.repository;

import com.cpcompass.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalRepository
        extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findByUserIdOrderByCreatedAtDesc(
            Long userId
    );
}