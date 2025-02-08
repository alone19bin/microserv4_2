package individuals.personservice.repository;

import individuals.personservice.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface IndividualRepository extends JpaRepository<Individual, UUID> {
    Optional<Individual> findByUserId(UUID userId);
}