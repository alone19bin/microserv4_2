package individuals.common.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class AddressDto {
    private UUID id;
    private String address;
    private String postalCode;
    private String city;
    private String state;
    private CountryDto country;
}