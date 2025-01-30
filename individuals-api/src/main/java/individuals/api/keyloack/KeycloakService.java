package individuals.api.keyloack;

import individuals.common.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

    private final KeycloakClient keycloakClient;

    public KeycloakService(KeycloakClient keycloakClient) {
        this.keycloakClient = keycloakClient;
    }

    public String registerUser(String firstName, String lastName, String email, String password) {
        return keycloakClient.createUser(firstName, lastName, email, password);
    }

    public void deleteUser(String userId) {
        keycloakClient.deleteUser(userId);
    }
}