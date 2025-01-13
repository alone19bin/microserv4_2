package individuals.common.dto;



import lombok.Data;
import java.io.Serializable;

@Data
public class IndividualDto implements Serializable {
    private String userId;
    private String passportNumber;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
}