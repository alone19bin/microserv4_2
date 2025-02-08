package individuals.personservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "individuals", schema = "person")
public class Individual {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

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