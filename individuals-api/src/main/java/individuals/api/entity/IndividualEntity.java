package individuals.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "individuals", schema = "person")
public class IndividualEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "archived_at")
    private LocalDateTime archivedAt;

    @Column(name = "status")
    private String status;
}