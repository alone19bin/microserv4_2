package com.example.individuals.saga;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import com.example.individuals.client.PersonServiceClient;
import com.example.individuals.model.IndividualStatus;
import com.example.individuals.service.KeycloakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateIndividualSagaRollbackTest {

    @Mock
    private PersonServiceClient personServiceClient;

    @Mock
    private KeycloakService keycloakService;

    @InjectMocks
    private CreateIndividualSaga createIndividualSaga;

    private IndividualDto individualDto;
    private UserDto userDto;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        
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
        
        // игнорировать ошибки keycloak
        createIndividualSaga.setIgnoreKeycloakErrors(true);
    }

    @Test
    void testRollbackWhenKeycloakFails() {
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(userId);
        createdUserDto.setEmail("test@example.com");

          // настройка моков и создание пользователя в person-service
        when(personServiceClient.createPerson(any(UserDto.class))).thenReturn(createdUserDto);
        
        // ошибка при создании пользователя в keycloak
        doThrow(new RuntimeException("Ошибка Keycloak")).when(keycloakService).createUser(any(IndividualDto.class));

        IndividualDto result = createIndividualSaga.execute(individualDto);

        assertNotNull(result);
        assertEquals(userId, result.getUser().getId());
        
        // проверка чтобы не было вызова метода удаления, так как ошибка keycloak обрабатывается
        verify(personServiceClient, never()).deletePerson(any(UUID.class));
    }

    @Test
    void testRollbackWhenKeycloakFailsAndNotIgnored() {
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(userId);
        createdUserDto.setEmail("test@example.com");

        // keycloak не игнорируем
        createIndividualSaga.setIgnoreKeycloakErrors(false);

        // Настройка для моков
        // создание пользователя в person-service
        when(personServiceClient.createPerson(any(UserDto.class))).thenReturn(createdUserDto);
        
        // ошибка при создании пользователя
        doThrow(new RuntimeException("Ошибка Keycloak")).when(keycloakService).createUser(any(IndividualDto.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createIndividualSaga.execute(individualDto);
        });

        assertTrue(exception.getMessage().contains("Ошибка при создании пользователя"));

        verify(personServiceClient).deletePerson(userId);
    }

    @Test
    void testRollbackWhenDeleteFails() {
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(userId);
        createdUserDto.setEmail("test@example.com");

        // устанавка флаг, чтобы ошибки keycloak не игнорировались
        createIndividualSaga.setIgnoreKeycloakErrors(false);

        // настройка моков и создание пользователя в person-service
        when(personServiceClient.createPerson(any(UserDto.class))).thenReturn(createdUserDto);
        
        //ошибка при создании пользователя в Keycloak
        doThrow(new RuntimeException("Ошибка Keycloak")).when(keycloakService).createUser(any(IndividualDto.class));
        
        //ошибка при удалении пользователя (когда откат не удался)
        doThrow(new RuntimeException("Ошибка удаления")).when(personServiceClient).deletePerson(any(UUID.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            createIndividualSaga.execute(individualDto);
        });

        assertTrue(exception.getMessage().contains("Ошибка при создании пользователя"));
        
        // Проверка что была попытка вызвать метод удаления пользоватея для отката изменений
        verify(personServiceClient).deletePerson(userId);
    }

    @Test
    void testKeycloakErrorHandling() {
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(userId);
        createdUserDto.setEmail("test@example.com");

        when(personServiceClient.createPerson(any(UserDto.class))).thenReturn(createdUserDto);
        
        doThrow(new RuntimeException("Ошибка Keycloak")).when(keycloakService).createUser(any(IndividualDto.class));

        IndividualDto result = createIndividualSaga.execute(individualDto);

        assertNotNull(result);
        assertEquals(userId, result.getUser().getId());
    }
} 