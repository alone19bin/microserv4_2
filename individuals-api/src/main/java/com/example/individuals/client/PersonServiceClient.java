package com.example.individuals.client;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PersonServiceClient {
    
    private final RestTemplate restTemplate;
    
    @Value("${person-service.url}")
    private String personServiceUrl;
    
    public UserDto createPerson(UserDto userDto) {
        return restTemplate.postForObject(
            personServiceUrl + "/api/v1/persons",
            userDto,
            UserDto.class
        );
    }
    
    public void deletePerson(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID canotbe null");
        }
        restTemplate.delete(personServiceUrl + "/api/v1/persons/" + userId);
    }
    
    public UserDto updatePerson(UserDto userDto) {
        return restTemplate.exchange(
            personServiceUrl + "/api/v1/persons",
            HttpMethod.PUT,
            new HttpEntity<>(userDto),
            UserDto.class
        ).getBody();
    }
    
    public IndividualDto getPersonByEmail(String email) {
        return restTemplate.getForObject(
            personServiceUrl + "/api/v1/persons/email/" + email,
            IndividualDto.class
        );
    }
} 