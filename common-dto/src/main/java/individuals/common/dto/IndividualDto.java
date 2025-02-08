package individuals.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class IndividualDto {
    private UUID id;
    private UUID userId;
    private UserDto user;
    private String passportNumber;
    private String phoneNumber;
    private String email;
    private LocalDateTime verifiedAt;
    private LocalDateTime archivedAt;
    private String status;
}