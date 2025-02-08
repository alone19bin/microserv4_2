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
public class AddressDto {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private CountryDto country;
    private String address;
    private String zipCode;
    private LocalDateTime archived;
    private String city;
    private String state;
}