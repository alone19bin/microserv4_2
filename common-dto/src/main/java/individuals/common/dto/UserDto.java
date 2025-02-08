    package individuals.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    public enum Role {
        USER, ADMIN, MODERATOR
    }

    private UUID id;
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Boolean filled;
}