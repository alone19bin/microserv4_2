package individuals.common.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PersonDto implements Serializable {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}