package com.example.individuals.service;

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
public class KeycloakServiceTest {

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
    private KeycloakService keycloakService;

    private IndividualDto individualDto;
    private UserDto userDto;
    private String realmName = "test-realm";

    @BeforeEach
    void setUp() {
            // значение для приватного поля realm
        ReflectionTestUtils.setField(keycloakService, "realm", realmName);

        userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Тест");
        userDto.setLastName("Тестов");

        individualDto = new IndividualDto();
        individualDto.setUser(userDto);
        individualDto.setEmail("test@example.com");
        individualDto.setPassportNumber("1234 567890");
        individualDto.setPhoneNumber("+7 (999) 123-45-67");
        individualDto.setStatus("ACTIVE");

        when(keycloak.realm(realmName)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
    }

    @Test
    void testCreateUserWhenUserDoesNotExist() {

        when(usersResource.search(anyString())).thenReturn(Collections.emptyList());
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(201); // Created

        verify(usersResource).create(any(UserRepresentation.class));
        verify(usersResource, never()).get(anyString());
    }

    @Test
    void testCreateUserWhenUserExists() {

        UserRepresentation existingUser = new UserRepresentation();
        existingUser.setId("user-id-123");
        existingUser.setEmail("test@example.com");

        when(usersResource.search(anyString())).thenReturn(Collections.singletonList(existingUser));
        when(usersResource.get(anyString())).thenReturn(userResource);
        doNothing().when(userResource).update(any(UserRepresentation.class));

        assertDoesNotThrow(() -> keycloakService.createUser(individualDto));

        verify(usersResource, never()).create(any(UserRepresentation.class));
        verify(userResource).update(any(UserRepresentation.class));
    }

    @Test
    void testCreateUserWithConflict() {

        UserRepresentation existingUser = new UserRepresentation();
        existingUser.setId("user-id-123");
        existingUser.setEmail("test@example.com");


            //не находим пользователя
        when(usersResource.search(anyString()))
                .thenReturn(Collections.emptyList())
                .thenReturn(Collections.singletonList(existingUser));   // потом находим (после конфликта)
        
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(409); // Conflict
        
        when(usersResource.get(anyString())).thenReturn(userResource);
        doNothing().when(userResource).update(any(UserRepresentation.class));

        assertDoesNotThrow(() -> keycloakService.createUser(individualDto));

        verify(usersResource).create(any(UserRepresentation.class));
        verify(userResource).update(any(UserRepresentation.class));
    }

    @Test
    void testCreateUserWithError() {

        when(usersResource.search(anyString())).thenReturn(Collections.emptyList());
        when(usersResource.create(any(UserRepresentation.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(500); // Internal Server Error
        when(response.getStatusInfo()).thenReturn(Response.Status.INTERNAL_SERVER_ERROR);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            keycloakService.createUser(individualDto);
        });

        assertTrue(exception.getMessage().contains("Failed to create user in Keycloak"));
    }

    @Test
    void testUpdateUserWhenUserExists() {

        UserRepresentation existingUser = new UserRepresentation();
        existingUser.setId("user-id-123");
        existingUser.setEmail("test@example.com");

        when(usersResource.search(anyString())).thenReturn(Collections.singletonList(existingUser));
        when(usersResource.get(anyString())).thenReturn(userResource);
        doNothing().when(userResource).update(any(UserRepresentation.class));

        assertDoesNotThrow(() -> keycloakService.updateUser(individualDto));

        verify(userResource).update(any(UserRepresentation.class));
    }

    @Test
    void testUpdateUserWhenUserDoesNotExist() {

        when(usersResource.search(anyString())).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            keycloakService.updateUser(individualDto);
        });

        assertTrue(exception.getMessage().contains("User not found in Keycloak"));
    }

    @Test
    void testDeleteUserWhenUserExists() {

        UserRepresentation existingUser = new UserRepresentation();
        existingUser.setId("user-id-123");
        existingUser.setEmail("test@example.com");

        when(usersResource.search(anyString())).thenReturn(Collections.singletonList(existingUser));
        when(usersResource.delete(anyString())).thenReturn(response);
        when(response.getStatus()).thenReturn(204); // No Content

        assertDoesNotThrow(() -> keycloakService.deleteUser("test@example.com"));

        verify(usersResource).delete(anyString());
    }

    @Test
    void testDeleteUserWhenUserDoesNotExist() {

        when(usersResource.search(anyString())).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            keycloakService.deleteUser("test@example.com");
        });

        assertTrue(exception.getMessage().contains("User not found in Keycloak"));
    }
} 