package com.example.individuals.saga;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import com.example.individuals.client.PersonServiceClient;
import com.example.individuals.service.KeycloakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateIndividualSagaTest {

    @Mock
    private PersonServiceClient personServiceClient;

    @Mock
    private KeycloakService keycloakService;

    @InjectMocks
    private CreateIndividualSaga createIndividualSaga;

    private IndividualDto individualDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        // Подготовка тестовых данных
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
    }

    @Test
    void testRollbackOnError() {

        UUID userId = UUID.randomUUID();
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(userId);
        createdUserDto.setEmail("test@example.com");

        createIndividualSaga.setIgnoreKeycloakErrors(false);

        when(personServiceClient.createPerson(any(UserDto.class))).thenReturn(createdUserDto);

        doThrow(new RuntimeException("Ошибка Keycloak")).when(keycloakService).createUser(any(IndividualDto.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createIndividualSaga.execute(individualDto);
        });

        assertTrue(exception.getMessage().contains("Ошибка при создании пользователя"));

        verify(personServiceClient).deletePerson(userId);
    }

    @Test
    void testRollbackOnPersonServiceError() {
        // Подготовка
        UUID userId = UUID.randomUUID();
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(userId);
        createdUserDto.setEmail("test@example.com");

        when(personServiceClient.createPerson(any(UserDto.class))).thenThrow(new RuntimeException("Ошибка создания пользователя"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createIndividualSaga.execute(individualDto);
        });

        assertTrue(exception.getMessage().contains("Ошибка при создании пользователя"));

        verify(personServiceClient, never()).deletePerson(any(UUID.class));
    }

    @Test
    void testSuccessfulCreation() {
        // Подготовка
        UUID userId = UUID.randomUUID();
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(userId);
        createdUserDto.setEmail("test@example.com");

        when(personServiceClient.createPerson(any(UserDto.class))).thenReturn(createdUserDto);
        doNothing().when(keycloakService).createUser(any(IndividualDto.class));

        IndividualDto result = createIndividualSaga.execute(individualDto);

        assertNotNull(result);
        assertEquals(userId, result.getUser().getId());
        verify(personServiceClient, never()).deletePerson(any(UUID.class));
    }

    @Test
    void testUpdateExistingIndividual() {

        UUID userId = UUID.randomUUID();
        UserDto existingUserDto = new UserDto();
        existingUserDto.setId(userId);
        existingUserDto.setEmail("test@example.com");

        IndividualDto existingIndividualDto = new IndividualDto();
        existingIndividualDto.setUser(existingUserDto);

        when(personServiceClient.getPersonByEmail(anyString())).thenReturn(existingIndividualDto);
        when(personServiceClient.updatePerson(any(UserDto.class))).thenReturn(existingUserDto);
        doNothing().when(keycloakService).updateUser(any(IndividualDto.class));

        IndividualDto result = createIndividualSaga.execute(individualDto);

        assertNotNull(result);
        assertEquals(userId, result.getUser().getId());
        verify(personServiceClient, never()).createPerson(any(UserDto.class));
        verify(personServiceClient).updatePerson(any(UserDto.class));
    }
} 