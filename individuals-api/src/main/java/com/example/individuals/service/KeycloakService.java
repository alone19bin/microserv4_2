package com.example.individuals.service;

import com.example.dto.IndividualDto;
import com.example.individuals.client.KeycloakClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {
    
    private final KeycloakClient keycloakClient;
    

    public void createUser(IndividualDto individualDto) {
        try {
            keycloakClient.createUser(individualDto);
        } catch (Exception e) {
            log.error("Ошибка при создании пользователя в Keycloak: {}", e.getMessage());
            throw new RuntimeException("Ошибка при создании пользователя в Keycloak: " + e.getMessage(), e);
        }
    }
    

    public void deleteUser(String email) {
        try {
            keycloakClient.deleteUser(email);
        } catch (Exception e) {
            log.error("Ошибка при удалении пользователяв Keycloak: {}", e.getMessage());
            throw new RuntimeException("Ошибка при удалении пользователя в Keycloak: " + e.getMessage(), e);
        }
    }
    

    public void updateUser(IndividualDto individualDto) {
        try {
            keycloakClient.updateUser(individualDto);
        } catch (Exception e) {
            log.error("Ошибка при обновлении пользователя в Keycloak: {}", e.getMessage());
            throw new RuntimeException("Ошибка при обновлении пользователя в Keycloak: " + e.getMessage(), e);
        }
    }
    

    public boolean userExists(String email) {
        try {
            return keycloakClient.userExists(email);
        } catch (Exception e) {
            log.error("Ошибка при проверке существования пользователя в Keycloak: {}", e.getMessage());
            throw new RuntimeException("Ошибка при проверке существования пользователя в Keycloak: " + e.getMessage(), e);
        }
    }
} 