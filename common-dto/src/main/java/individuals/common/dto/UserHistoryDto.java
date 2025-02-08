package individuals.common.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class UserHistoryDto {
    private UUID id;

    @NotNull(message = "Created timestamp cannot be null")
    private LocalDateTime created;

    @NotNull(message = "User is required")
    private UserDto user;

    @Size(max = 32, message = "User type must be less than 32 characters")
    private String userType;

    @Size(max = 255, message = "Reason must be less than 255 characters")
    private String reason;

    @Size(max = 255, message = "Comment must be less than 255 characters")
    private String comment;

    private Map<String, Object> changedValues;
}