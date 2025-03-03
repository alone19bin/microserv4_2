package com.example.individuals.client;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeycloakClientTest {

    @Mock
    private Keycloak keycloak;

    @Mock
    private RealmResource realmResource;

    @Mock
    private UsersResource usersResource;

    @Mock
    private UserResource userResource;

    @Mock
    private Response response;

    @InjectMocks
    private KeycloakClient keycloakClient;

    private IndividualDto individualDto;
    private UserDto userDto;
    private String realmName = "test-realm";
    private String userEmail = "test@example.com";

    @BeforeEach
    void setUp() {
           // значение для приватного поля realm
        ReflectionTestUtils.setField(keycloakClient, "realm", realmName);


        userDto = new UserDto();
        userDto.setEmail(userEmail);
        userDto.setFirstName("Тест");
        userDto.setLastName("Тестов");

        individualDto = new IndividualDto();
        individualDto.setUser(userDto);
        individualDto.setEmail(userEmail);
        individualDto.setPassportNumber("1234 567890");
        individualDto.setPhoneNumber("+7 (999) 123-45-67");
        individualDto.setStatus("ACTIVE");

        // Настройка  моков
        when(keycloak.realm(realmName)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
    }

    @Test
    void testCreateUserWhenUserDoesNotExist() {
        // Настройка моков
        when(usersResource.search(anyString())).thenReturn(Collections.emptyList());
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(201); // Created


        boolean result = keycloakClient.createUser(individualDto);

           //проверяем
        assertTrue(result);
        verify(usersResource).create(any(UserRepresentation.class));
        verify(usersResource, never()).get(anyString());
    }

    @Test
    void testCreateUserWhenUserExists() {

        String userId = "user-id-123";
        List<UserRepresentation> existingUsers = new ArrayList<>();
        UserRepresentation existingUser = new UserRepresentation();
        existingUser.setId(userId);
        existingUser.setEmail(userEmail);
        existingUsers.add(existingUser);


        when(usersResource.search(userEmail)).thenReturn(existingUsers);
        when(usersResource.get(userId)).thenReturn(userResource);
        doNothing().when(userResource).update(any(UserRepresentation.class));

        boolean result = keycloakClient.createUser(individualDto);

        assertTrue(result);
        verify(usersResource, never()).create(any(UserRepresentation.class));
        verify(userResource).update(any(UserRepresentation.class));
    }

    @Test
    void testCreateUserWithConflict() {
            //пользователь не найден
        when(usersResource.search(userEmail)).thenReturn(Collections.emptyList())
                                            .thenReturn(Collections.singletonList(createUserRepresentation("user-id-123")));
        
             //возвращает конфликт
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(409); // Conflict
        
          //  получение пользователя после конфликта
        when(usersResource.get(anyString())).thenReturn(userResource);
        doNothing().when(userResource).update(any(UserRepresentation.class));

        boolean result = keycloakClient.createUser(individualDto);

        assertTrue(result);
        verify(usersResource).create(any(UserRepresentation.class));
        verify(userResource).update(any(UserRepresentation.class));
    }

    @Test
    void testCreateUserWithError() {

        when(usersResource.search(userEmail)).thenReturn(Collections.emptyList());
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(400); // Bad Request
        when(response.getStatusInfo()).thenReturn(Response.Status.BAD_REQUEST);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            keycloakClient.createUser(individualDto);
        });

        assertTrue(exception.getMessage().contains("Не удалось создать пользователя в Keycloak"));
    }

    @Test
    void testDeleteUser() {

        String userId = "user-id-123";
        List<UserRepresentation> existingUsers = new ArrayList<>();
        UserRepresentation existingUser = new UserRepresentation();
        existingUser.setId(userId);
        existingUser.setEmail(userEmail);
        existingUsers.add(existingUser);

        when(usersResource.search(userEmail)).thenReturn(existingUsers);
        when(usersResource.delete(userId)).thenReturn(response);
        when(response.getStatus()).thenReturn(204); // No Content

        boolean result = keycloakClient.deleteUser(userEmail);

        assertTrue(result);
        verify(usersResource).delete(userId);
    }

    @Test
    void testDeleteUserNotFound() {

        when(usersResource.search(userEmail)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            keycloakClient.deleteUser(userEmail);
        });
        assertTrue(exception.getMessage().contains("Пользователь не найден в Keycloak"));
        verify(usersResource, never()).delete(anyString());
    }

    @Test
    void testUpdateUser() {

        String userId = "user-id-123";
        List<UserRepresentation> existingUsers = new ArrayList<>();
        UserRepresentation existingUser = new UserRepresentation();
        existingUser.setId(userId);
        existingUser.setEmail(userEmail);
        existingUsers.add(existingUser);

        when(usersResource.search(userEmail)).thenReturn(existingUsers);
        when(usersResource.get(userId)).thenReturn(userResource);
        doNothing().when(userResource).update(any(UserRepresentation.class));

        boolean result = keycloakClient.updateUser(individualDto);


        assertTrue(result);
        verify(userResource).update(any(UserRepresentation.class));
    }

    @Test
    void testUpdateUserNotFound() {

        when(usersResource.search(userEmail)).thenReturn(Collections.emptyList());


        Exception exception = assertThrows(RuntimeException.class, () -> {
            keycloakClient.updateUser(individualDto);
        });

        assertTrue(exception.getMessage().contains("Пользователь не найден в Keycloak"));
        verify(userResource, never()).update(any(UserRepresentation.class));
    }

    @Test
    void testUserExists() {

        List<UserRepresentation> existingUsers = new ArrayList<>();
        existingUsers.add(new UserRepresentation());

        when(usersResource.search(userEmail)).thenReturn(existingUsers);
        boolean result = keycloakClient.userExists(userEmail);
        assertTrue(result);
    }

    @Test
    void testUserDoesNotExist() {
        when(usersResource.search(userEmail)).thenReturn(Collections.emptyList());
        boolean result = keycloakClient.userExists(userEmail);
        assertFalse(result);
    }

    @Test
    void testGetUserByEmail() {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(userEmail);
        List<UserRepresentation> existingUsers = Collections.singletonList(userRepresentation);
        when(usersResource.search(userEmail)).thenReturn(existingUsers);
        UserRepresentation result = keycloakClient.getUserByEmail(userEmail);
        assertNotNull(result);
        assertEquals(userEmail, result.getEmail());
    }

    @Test
    void testGetUserByEmailNotFound() {
        when(usersResource.search(userEmail)).thenReturn(Collections.emptyList());
        UserRepresentation result = keycloakClient.getUserByEmail(userEmail);
        assertNull(result);
    }

          //для создания UserRepresentation
    private UserRepresentation createUserRepresentation(String userId) {
        UserRepresentation user = new UserRepresentation();
        user.setId(userId);
        user.setEmail(userEmail);
        return user;
    }
} 