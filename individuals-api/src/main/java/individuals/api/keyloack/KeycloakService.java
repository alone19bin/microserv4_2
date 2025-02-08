package individuals.api.keyloack;

import individuals.common.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KeycloakService {
    private final KeycloakClient keycloakClient;

    public KeycloakService(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    public String registerUser(UserDto user, String password) {
        return keycloakClient.createUser(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                password
        );
    }

    public void deleteUser(String userId) {
        keycloakClient.deleteUser(userId);
    }
}