package individuals.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    @NotBlank(message = "Имя обязательно")
    private String firstName;

    @NotBlank(message = "Фамилия    обязательна")
    private String lastName;

    @Email(message = "Некорректный email")
    @NotBlank(message = " Email обязателен")
    private String email;

    @NotBlank(message = "Пароль обязателен")
    private String password;

    @NotBlank(message = "Номер паспорта обязателен")
    private String passportNumber;

    @NotBlank(message = "Номер  телефона обязателен")
    private String phoneNumber;

    public UserDto toUserDTO() {
        return UserDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();
    }

    public IndividualDto toIndividualDTO() {
        return IndividualDto.builder()
                .passportNumber(passportNumber)
                .phoneNumber(phoneNumber)
                .build();
    }
}