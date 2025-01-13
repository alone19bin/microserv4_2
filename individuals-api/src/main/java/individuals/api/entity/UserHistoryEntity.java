package individuals.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_history", schema = "person")
public class UserHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "reason")
    private String reason;

    @Column(name = "comment")
    private String comment;
}