package individuals.personservice.repository;

import individuals.personservice.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;



@Repository
public interface IndividualRepository extends JpaRepository<Individual, UUID> {}
