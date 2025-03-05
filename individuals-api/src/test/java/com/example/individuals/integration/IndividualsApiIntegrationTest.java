package com.example.individuals.integration;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import com.example.individuals.IndividualsApiApplication;
import com.example.individuals.client.KeycloakClient;
import com.example.individuals.client.PersonServiceClient;
import com.example.individuals.model.IndividualStatus;
import com.example.individuals.saga.CreateIndividualSaga;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(
    classes = IndividualsApiApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
public class IndividualsApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private KeycloakClient keycloakClient;

    @MockBean
    private PersonServiceClient personServiceClient;

    @Autowired
    private CreateIndividualSaga createIndividualSaga;

    private static final String BASE_URL = "/api/v1/individuals";
    private UserDto testUser;
    private IndividualDto testIndividual;

    @BeforeEach
    void setUp() {
        testUser = new UserDto();
        testUser.setId(UUID.randomUUID());
        testUser.setEmail("test@example.com");
        testUser.setFirstName("Test");
        testUser.setLastName("User");

        testIndividual = new IndividualDto();
        testIndividual.setId(testUser.getId());
        testIndividual.setEmail(testUser.getEmail());
        testIndividual.setUser(testUser);
        testIndividual.setStatus("ACTIVE");

            //флаг ignoreKeycloakErrors в false для всех тестов
        createIndividualSaga.setIgnoreKeycloakErrors(false);
    }

    @Test
    void testCreateIndividual() {
        when(keycloakClient.createUser(any())).thenReturn(true);
        when(personServiceClient.createPerson(any())).thenReturn(testUser);

         //запрос
        ResponseEntity<IndividualDto> response = restTemplate.postForEntity(
            BASE_URL,
            testIndividual,
            IndividualDto.class
        );

         //результат
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser.getEmail(), response.getBody().getEmail());
        assertNotNull(response.getBody().getUser());
        assertEquals(testUser.getFirstName(), response.getBody().getUser().getFirstName());
        assertEquals(testUser.getLastName(), response.getBody().getUser().getLastName());
        
        // Проверка вызова  методов
        verify(keycloakClient).createUser(any());
        verify(personServiceClient).createPerson(any());
    }

    @Test
    void testUpdateIndividual() {
        when(keycloakClient.updateUser(any())).thenReturn(true);
        when(personServiceClient.updatePerson(any())).thenReturn(testUser);

        //Обновить
        testUser.setFirstName("Updated");
        testUser.setLastName("Name");
        testIndividual.setUser(testUser);

        ResponseEntity<IndividualDto> response = restTemplate.exchange(
            BASE_URL,
            HttpMethod.PUT,
            new HttpEntity<>(testIndividual),
            IndividualDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser.getEmail(), response.getBody().getEmail());
        assertEquals(testUser.getFirstName(), response.getBody().getUser().getFirstName());
        assertEquals(testUser.getLastName(), response.getBody().getUser().getLastName());

        verify(keycloakClient).updateUser(any());
        verify(personServiceClient).updatePerson(any());
    }

    @Test
    void testDeleteIndividual() {
        when(keycloakClient.deleteUser(any(String.class))).thenReturn(true);
        willDoNothing().given(personServiceClient).deletePerson(any());
        when(personServiceClient.getPersonByEmail(any())).thenReturn(testIndividual);

        restTemplate.delete(BASE_URL + "/" + testUser.getId());
        verify(keycloakClient).deleteUser(any(String.class));
        verify(personServiceClient).deletePerson(any());
    }

    @Test
    void testGetIndividualByEmail() {

        when(personServiceClient.getPersonByEmail(any())).thenReturn(testIndividual);

        ResponseEntity<IndividualDto> response = restTemplate.getForEntity(
            BASE_URL + "/by-email/" + testUser.getEmail(),
            IndividualDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testUser.getEmail(), response.getBody().getEmail());
        verify(personServiceClient).getPersonByEmail(testUser.getEmail());
    }

    @Test
    void testUpdateIndividualWithPersonServiceError() {

        when(personServiceClient.updatePerson(any())).thenThrow(new RuntimeException("Personservice error"));
        ResponseEntity<String> response = restTemplate.exchange(
            BASE_URL,
            HttpMethod.PUT,
            new HttpEntity<>(testIndividual),
            String.class
        );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Person service error"));

        verify(personServiceClient).updatePerson(any());
        verify(keycloakClient, never()).updateUser(any());
    }

    @Test
    void testCreateIndividualWithKeycloakError() {

        UserDto createdUser = new UserDto();
        createdUser.setId(UUID.randomUUID());
        createdUser.setEmail("test@example.com");
        
        when(personServiceClient.getPersonByEmail(anyString())).thenReturn(null);
        when(personServiceClient.createPerson(any())).thenReturn(createdUser);
        doThrow(new RuntimeException("Keycloak error")).when(keycloakClient).createUser(any());


        ResponseEntity<String> response = restTemplate.postForEntity(
            BASE_URL,
            testIndividual,
            String.class
        );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(personServiceClient, times(1)).createPerson(any());
        verify(keycloakClient, times(1)).createUser(any());
        verify(personServiceClient, times(1)).deletePerson(createdUser.getId());
    }
} 