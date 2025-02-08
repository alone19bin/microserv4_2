package individuals.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PersonDto implements Serializable {
    @JsonProperty("id")
    private UUID id;

    @NotBlank(message = "First name is required")
    @Size(max = 32, message = " First name must be less than 32 characters")
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank(message = "Last  na  me is required")
    @Size(max = 32, message = "l ast name must be less than 32 character s")
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;

    @Valid
    @JsonProperty("individual")
    private IndividualDto individual;

    @Valid
    @JsonProperty("user")
    private UserDto user;
}