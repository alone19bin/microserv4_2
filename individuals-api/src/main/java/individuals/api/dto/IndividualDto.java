package individuals.api.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class IndividualDto implements Serializable {
    private String passportNumber;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
}