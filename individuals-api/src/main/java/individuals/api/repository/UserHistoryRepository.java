package individuals.api.repository;

import individuals.api.entity.UserHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistoryEntity, UUID> {
    List<UserHistoryEntity> findByUser_Id(UUID userId);
    List<UserHistoryEntity> findByUserType(String userType);
}