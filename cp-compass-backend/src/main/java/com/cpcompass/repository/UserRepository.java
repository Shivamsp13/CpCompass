package com.cpcompass.repository;

import com.cpcompass.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByCodeforcesHandle(String codeforcesHandle);
    boolean existsByEmail(String email);

    boolean existsByCodeforcesHandle(String codeforcesHandle);
}