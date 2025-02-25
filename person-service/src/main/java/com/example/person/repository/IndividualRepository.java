package com.example.person.repository;

import com.example.person.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IndividualRepository extends JpaRepository<Individual, UUID> {
    void deleteByUserId(UUID userId);
    Optional<Individual> findByUserId(UUID userId);
} 