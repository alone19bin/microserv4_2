package individuals.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto implements Serializable {
    @JsonProperty("id")
    private String id;

    @NotBlank(message = "Country name is required")
    @Size(max = 32, message = "  Country name must be less than 32 characters")
    @JsonProperty("name")
    private String name;

    @JsonProperty("countryCode")
    private String countryCode;

    @NotBlank(message = "  Alpha-2 code is required")
    @Size(max = 2, message = "Alpha-2 code must be 2 characters")
    @JsonProperty("alpha2")
    private String alpha2;

    @NotBlank(message = "Alpha-3 code is required")
    @Size(max = 3, message = "Alp ha-3 code must be 3 characters")
    @JsonProperty("alpha3")
    private String alpha3;

    @JsonProperty("status")
    private String status;

    @JsonProperty("created")
    private LocalDateTime created;

    @JsonProperty("updated")
    private LocalDateTime updated;



}