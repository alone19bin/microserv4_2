package com.example.individuals.service;

import com.example.dto.IndividualDto;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    
    private final Keycloak keycloak;
    
    @Value("${keycloak.realm}")
    private String realm;
    
    public void createUser(IndividualDto individualDto) {
        try {
            String email = individualDto.getUser().getEmail();
            
              // gроверяка существует ли пользователь с таким email
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> existingUsers = usersResource.search(email);
            
            if (!existingUsers.isEmpty()) {
                   // если уже существует, обновляем его данные
                String userId = existingUsers.get(0).getId();
                
                UserRepresentation user = new UserRepresentation();
                user.setUsername(email);
                user.setEmail(email);
                user.setFirstName(individualDto.getUser().getFirstName());
                user.setLastName(individualDto.getUser().getLastName());
                user.setEnabled(true);
                user.setEmailVerified(true);
                
                usersResource.get(userId).update(user);
                return;
            }
            
               // создание нового пользователя
            UserRepresentation user = new UserRepresentation();
            user.setUsername(email);
            user.setEmail(email);
            user.setFirstName(individualDto.getUser().getFirstName());
            user.setLastName(individualDto.getUser().getLastName());
            user.setEnabled(true);
            user.setEmailVerified(true);
            
              // Добавние временного  пароля
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue("temp123");
            credential.setTemporary(true);
            
            user.setCredentials(Collections.singletonList(credential));
            
            Response response = usersResource.create(user);
            
            if (response.getStatus() == 409) {
                  // Если  409 (конфликт), то пользователь появился между нашими проверками?
                    // далее обновить существующего пользователя
                existingUsers = usersResource.search(email);
                if (!existingUsers.isEmpty()) {
                    String userId = existingUsers.get(0).getId();
                    usersResource.get(userId).update(user);
                } else {
                    throw new RuntimeException("Failed to cr eate user in Keycloak. Status: " + response.getStatus() + " - " + response.getStatusInfo().getReasonPhrase());
                }
            } else if (response.getStatus() < 200 || response.getStatus() >= 300) {
                throw new RuntimeException("Failedto create user in Keycloak. Status: " + response.getStatus() + " - " + response.getStatusInfo().getReasonPhrase());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user in Keycloak: " + e.getMessage(), e);
        }
    }
    
    public void deleteUser(String email) {
        try {
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(email);
            
            if (users.isEmpty()) {
                throw new RuntimeException("Usernot found in Keycloak: " + email);
            }
            
            String userId = users.get(0).getId();
            Response response = usersResource.delete(userId);
            
            if (response.getStatus() < 200 || response.getStatus() >= 300) {
                throw new RuntimeException("Failed to delete user in Keycloak. Status: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user in Keycloak: " + e.getMessage(), e);
        }
    }
    
    public void updateUser(IndividualDto individualDto) {
        try {
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(individualDto.getUser().getEmail());
            
            if (users.isEmpty()) {
                throw new RuntimeException("User not found in Keycloak: " + individualDto.getUser().getEmail());
            }
            
            String userId = users.get(0).getId();
            
            UserRepresentation user = new UserRepresentation();
            user.setUsername(individualDto.getUser().getEmail());
            user.setEmail(individualDto.getUser().getEmail());
            user.setFirstName(individualDto.getUser().getFirstName());
            user.setLastName(individualDto.getUser().getLastName());
            user.setEnabled(true);
            
            usersResource.get(userId).update(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user in Keycloak: " + e.getMessage(), e);
        }
    }
} 