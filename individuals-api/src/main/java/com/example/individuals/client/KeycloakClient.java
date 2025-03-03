package com.example.individuals.client;

import com.example.dto.IndividualDto;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KeycloakClient {
    
    private final Keycloak keycloak;
    
    @Value("${keycloak.realm}")
    private String realm;
    

    public boolean createUser(IndividualDto individualDto) {
        try {
            String email = individualDto.getUser().getEmail();
            
            //существует ли пользователь стаким email
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> existingUsers = usersResource.search(email);
            
            if (!existingUsers.isEmpty()) {
                    // Если уже существует, обновляем данные
                String userId = existingUsers.get(0).getId();
                
                UserRepresentation user = new UserRepresentation();
                user.setUsername(email);
                user.setEmail(email);
                user.setFirstName(individualDto.getUser().getFirstName());
                user.setLastName(individualDto.getUser().getLastName());
                user.setEnabled(true);
                user.setEmailVerified(true);
                
                usersResource.get(userId).update(user);
                return true;
            }
            
              //Создание нового пользователя
            UserRepresentation user = new UserRepresentation();
            user.setUsername(email);
            user.setEmail(email);
            user.setFirstName(individualDto.getUser().getFirstName());
            user.setLastName(individualDto.getUser().getLastName());
            user.setEnabled(true);
            user.setEmailVerified(true);
            
              // временный пароль
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue("temp123");
            credential.setTemporary(true);
            
            user.setCredentials(Collections.singletonList(credential));
            
            Response response = usersResource.create(user);
            
            if (response.getStatus() == 409) {
                 //Если 409 (конфликт ), то пользователь появился между нашими проверками, то пробуем обновить существующего пользователя

                existingUsers = usersResource.search(email);
                if (!existingUsers.isEmpty()) {
                    String userId = existingUsers.get(0).getId();
                    usersResource.get(userId).update(user);
                    return true;
                } else {
                    throw new RuntimeException("Не удалосьсоздать пользователя в Keycloak. Статус: " + response.getStatus() + " - " + response.getStatusInfo().getReasonPhrase());
                }
            } else if (response.getStatus() < 200 || response.getStatus() >= 300) {
                throw new RuntimeException("Не удалось создать пользователя в Keycloak. Статус: " + response.getStatus() + " - " + response.getStatusInfo().getReasonPhrase());
            }
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании пользователя в Keycloak: " + e.getMessage(), e);
        }
    }
    

    public boolean deleteUser(String email) {
        try {
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(email);
            
            if (users.isEmpty()) {
                throw new RuntimeException(" Пользователь не найден в Keycloak: " + email);
            }
            
            String userId = users.get(0).getId();
            Response response = usersResource.delete(userId);
            
            if (response.getStatus() < 200 || response.getStatus() >= 300) {
                throw new RuntimeException("Не удалось удалить пользователя в Keycloak. Статус: " + response.getStatus());
            }
            
            return true;
        } catch (Exception e) {
            throw new RuntimeException(" Ошибка при удалении пользователя в Keycloak: " + e.getMessage(), e);
        }
    }
    

    public boolean updateUser(IndividualDto individualDto) {
        try {
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(individualDto.getUser().getEmail());
            
            if (users.isEmpty()) {
                throw new RuntimeException("Пользователь не найден в Keycloak: " + individualDto.getUser().getEmail());
            }
            
            String userId = users.get(0).getId();
            
            UserRepresentation user = new UserRepresentation();
            user.setUsername(individualDto.getUser().getEmail());
            user.setEmail(individualDto.getUser().getEmail());
            user.setFirstName(individualDto.getUser().getFirstName());
            user.setLastName(individualDto.getUser().getLastName());
            user.setEnabled(true);
            
            usersResource.get(userId).update(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении пользователя в Keycloak: " + e.getMessage(), e);
        }
    }
    

    public boolean userExists(String email) {
        try {
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(email);
            return !users.isEmpty();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при проверке существования пользователя в Keycloak: " + e.getMessage(), e);
        }
    }
    

     //получение пользователя из Keycloak по email

    public UserRepresentation getUserByEmail(String email) {
        try {
            UsersResource usersResource = keycloak.realm(realm).users();
            List<UserRepresentation> users = usersResource.search(email);
            
            if (users.isEmpty()) {
                return null;
            }
            
            return users.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении пользователя из Keycloak: " + e.getMessage(), e);
        }
    }
} 