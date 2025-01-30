package individuals.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddressDto {
    @NotNull(message = "ID адреса  обязателен")
    private UUID id;

    @NotBlank(message = " Страна о бязательна")
    private String country;

    @NotBlank(message = "Адрес обязателен")
    @Size(max = 128, message = "Адрес не должен превышать 128 символов")
    private String address;

    @NotBlank(message = "Почтовый   индекс обязателен")
    @Size(max = 32, message = "Почтовый индекс не должен превышать  32 символов")
    private String zipCode;

    private String city;
    private String state;
}