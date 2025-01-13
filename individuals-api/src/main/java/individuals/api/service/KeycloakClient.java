package main.java.individuals.api.service;

import individuals.api.config.KeycloakInstance;
import individuals.api.dto.IndividualDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakClient {
    private final KeycloakInstance keycloakInstance;

    public void registerUser(String userId, IndividualDto individualDto) {
        // Регистрация пользователя в Keycloak
        keycloakInstance.createUser(
                userId,
                individualDto.getEmail(),
                individualDto.getFirstName(),
                individualDto.getLastName()
        );
    }
}