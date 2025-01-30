    package individuals.common.dto;

    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.UUID;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDto {
        private UUID id;

        @NotBlank(message = "Имя  обязательно")
        private String firstName;

        @NotBlank(message = "Фамилия обязательна")
        private String lastName;

        @Email(message = " Некорректный email")
        @NotBlank(message = " Email обязателен")
        private String email;

        @NotBlank(message = "Пароль обязателен")
        private String password;

    }