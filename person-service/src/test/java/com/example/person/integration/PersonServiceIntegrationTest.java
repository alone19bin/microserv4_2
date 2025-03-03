package com.example.person.integration;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PersonServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.0")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private UserDto testUserDto;
    private IndividualDto testIndividualDto;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.enabled", () -> "true");
        registry.add("spring.flyway.schemas", () -> "person");
    }

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/persons";
        
        testUserDto = new UserDto();
        testUserDto.setEmail("test@example.com");
        testUserDto.setFirstName("Тест");
        testUserDto.setLastName("Тестов");
        testUserDto.setFilled(true);

        testIndividualDto = new IndividualDto();
        testIndividualDto.setUser(testUserDto);
        testIndividualDto.setEmail("test@example.com");
        testIndividualDto.setPassportNumber("1234 567890");
        testIndividualDto.setPhoneNumber("+7 (999) 123-45-67");
        testIndividualDto.setStatus("ACTIVE");
        testIndividualDto.setVerifiedAt(LocalDateTime.now());
        testIndividualDto.setArchivedAt(LocalDateTime.now().plusYears(1));
    }

    @Test
    void testCreatePerson() {
        ResponseEntity<UserDto> response = restTemplate.postForEntity(
                baseUrl,
                testUserDto,
                UserDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDto createdUser = response.getBody();
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals(testUserDto.getEmail(), createdUser.getEmail());
    }

    @Test
    void testGetPersonByEmail() {
            //создаем пользователя
        ResponseEntity<UserDto> createResponse = restTemplate.postForEntity(
                baseUrl,
                testUserDto,
                UserDto.class
        );
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

          //получаем его по email
        ResponseEntity<IndividualDto> response = restTemplate.getForEntity(
                baseUrl + "/email/" + testUserDto.getEmail(),
                IndividualDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        IndividualDto retrievedPerson = response.getBody();
        assertNotNull(retrievedPerson);
        assertEquals(testUserDto.getEmail(), retrievedPerson.getEmail());
    }

    @Test
    void testUpdatePerson() {
        ResponseEntity<UserDto> createResponse = restTemplate.postForEntity(
                baseUrl,
                testUserDto,
                UserDto.class
        );
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        UserDto createdUser = createResponse.getBody();
        assertNotNull(createdUser);

          //обновляем данные
        createdUser.setFirstName("Обновленный");
        createdUser.setLastName("Пользователь");

        ResponseEntity<UserDto> updateResponse = restTemplate.exchange(
                baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(createdUser),
                UserDto.class
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        UserDto updatedUser = updateResponse.getBody();
        assertNotNull(updatedUser);
        assertEquals("Обновленный", updatedUser.getFirstName());
        assertEquals("Пользователь", updatedUser.getLastName());
    }

    @Test
    void testDeletePerson() {
        ResponseEntity<UserDto> createResponse = restTemplate.postForEntity(
                baseUrl,
                testUserDto,
                UserDto.class
        );
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        UserDto createdUser = createResponse.getBody();
        assertNotNull(createdUser);

                 //Удалить связанную запись из таблицы individuals
        restTemplate.delete(baseUrl + "/individual/" + createdUser.getId());

          //удаляить пользователя
        restTemplate.delete(baseUrl + "/" + createdUser.getId());

        // Проверка удаления
        ResponseEntity<IndividualDto> response = restTemplate.getForEntity(
                baseUrl + "/email/" + testUserDto.getEmail(),
                IndividualDto.class
        );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
} 