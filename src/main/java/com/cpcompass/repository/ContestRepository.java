package com.cpcompass.repository;

import com.cpcompass.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    List<Contest> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}