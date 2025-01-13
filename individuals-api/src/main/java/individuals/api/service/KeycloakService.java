package individuals.api.service;

import individuals.api.config.KeycloakInstance;
import individuals.common.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final KeycloakInstance keycloakInstance;

    public void registerUser(String userId, PersonDto personDto) {
        keycloakInstance.createUser(
                userId,
                personDto.getEmail(),
                personDto.getFirstName(),
                personDto.getLastName()
        );
    }
}