package individuals.common.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserRegistrationRequest {
    @Valid
    @NotNull(message = "Данные пользователя обязательны")
    private UserDto user;

    @Valid
    @NotNull(message = "Данные индивидуала обязательны")
    private IndividualDto individual;

    @Valid
    private AddressDto address;

                 //создания запроса сполной валидацией и связыванием
    public static UserRegistrationRequest create(
            String email, 
            String firstName, 
            String lastName, 
            String passportNumber, 
            String phoneNumber
    ) {
        LocalDateTime now = LocalDateTime.now();
        UUID id = UUID.randomUUID();

        UserDto user = UserDto.builder()
            .id(id)
            .email(email)
            .firstName(firstName)
            .lastName(lastName)
            .created(now)
            .updated(now)
            .filled(false)
            .build();

        IndividualDto individual = IndividualDto.builder()
            .user(user)
            .passportNumber(passportNumber)
            .phoneNumber(phoneNumber)
            .verifiedAt(now)
            .archivedAt(now)
            .status("ACTIVE")
            .build();

        return UserRegistrationRequest.builder()
            .user(user)
            .individual(individual)
            .build();
    }
}