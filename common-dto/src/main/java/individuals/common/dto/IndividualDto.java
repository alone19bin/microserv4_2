package individuals.common.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class IndividualDto {
    @NotNull(message = "ID  пользователя обязателен")
    private UUID userId;

    @NotBlank(message = "Номер паспорта обязателен")
    @Size(max = 32, message = "Номер паспорта не должен превышать 32 символов")
    private String passportNumber;

    @NotBlank(message = " Номер телефона обязателен")
    @Size(max = 32, message = "Номертелефона не должен превышать 32 символов")
    private String phoneNumber;

    @Email(message = "Некорректный email")
    private String email;

    private String firstName;
    private String lastName;

    private LocalDateTime verifiedAt;
    private String status;
}