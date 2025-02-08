package individuals.personservice.client;



import individuals.common.dto.UserDto;
import individuals.common.dto.IndividualDto;
import org.springframework.stereotype.Component;

@Component
public class PersonServiceClient {
    public UserDto createUser(UserDto userDto) {
        return userDto;
    }

    public IndividualDto createIndividual(IndividualDto individualDto) {
        return individualDto;
    }

    public UserDto findUserByEmail(String email) {
        return null;
    }
}