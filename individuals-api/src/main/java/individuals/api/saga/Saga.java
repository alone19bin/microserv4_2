package individuals.api.saga;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sagas", schema = "person")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Saga {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;
    private UUID individualId;
    private String keycloakUserId;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private SagaStatus status = SagaStatus.STARTED;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
}