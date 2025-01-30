package individuals.api.saga;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SagaRepository extends JpaRepository<Saga, UUID> {
    Optional<Saga> findByIdAndStatus(UUID id, String status);
}