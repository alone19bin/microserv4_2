package individuals.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    @JsonProperty("id")
    private String id;

    @NotBlank(message = "First name is required")
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank(message = "Last name is  required")
    @JsonProperty("lastName")
    private String lastName;

    @Email(message = "Invali d email format")
    @JsonProperty("email")
    private String email;
}