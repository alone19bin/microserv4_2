package com.example.individuals.service;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import com.example.individuals.client.PersonServiceClient;
import com.example.individuals.saga.CreateIndividualSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndividualService {
    private final PersonServiceClient personServiceClient;
    private final KeycloakService keycloakService;
    private final CreateIndividualSaga createIndividualSaga;

    public IndividualDto createIndividual(IndividualDto individualDto) {
        return createIndividualSaga.execute(individualDto);
    }

    public void deleteIndividual(IndividualDto dto) {
        if (dto == null || dto.getUser() == null) {
            throw new IllegalArgumentException("Пользователь не указан");
        }
        
        String email = dto.getUser().getEmail();
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email пользователя не указан");
        }
        
            //если ID не указан то сначала получаем пользователя по email
        if (dto.getUser().getId() == null) {
            try {
                IndividualDto existingIndividual = personServiceClient.getPersonByEmail(email);
                if (existingIndividual != null && existingIndividual.getUser() != null && existingIndividual.getUser().getId() != null) {
                    dto.getUser().setId(existingIndividual.getUser().getId());
                } else {
                    throw new IllegalArgumentException("Неудалось найти ID пользователя с email: " + email);
                }
            } catch (Exception e) {
                log.error("Ошибка при поиске пользователя по email {}: {}", email, e.getMessage());
                throw new RuntimeException("Не удалось найти пользователя с email: " + email, e);
            }
        }
        
        UUID userId = dto.getUser().getId();
        if (userId == null) {
            throw new IllegalArgumentException("ID пользователя не указан и не может быть получен по email");
        }
        
            //Сначала удаляем из keycloak
        try {
            keycloakService.deleteUser(email);
        } catch (Exception e) {
            log.warn("Ошибка при удалении пользователя из Keycloak: {}", e.getMessage());
            // продолжить выполнение, чтобы удалить пользователя из personervice
        }
        
               //удалить из person-service
        personServiceClient.deletePerson(userId);
    }
    public void deleteIndividualById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID пользователя не указан");
        }

                        //Получение пользователя по id  через email, также можно  удалить из keycloak
        String email = null;
        try {
            IndividualDto individual = personServiceClient.getPersonByEmail(null); // Предполагается, что это временный вызов, нужен реальный способ получения email
            if (individual != null && individual.getUser() != null) {
                email = individual.getUser().getEmail();
            }
        } catch (Exception e) {
            log.warn("Не удалось получить email для пользователя с ID {}: {}", id, e.getMessage());
        }
         //удаление из Keycloak, если email нам известен
        if (email != null && !email.isEmpty()) {
            try {
                keycloakService.deleteUser(email);
            } catch (Exception e) {
                log.warn("Ошибка при удалении пользователя из Keycloak по ID {}: {}", id, e.getMessage());

            }
        } else {
            log.warn("Email пользователя с ID {} неизвестен, удаление из Keycloak пропущено", id);
        }

        // удаление из person-service
        personServiceClient.deletePerson(id);
    }



    public IndividualDto updateIndividual(IndividualDto individualDto) {
        UserDto updatedUser = personServiceClient.updatePerson(individualDto.getUser());
        keycloakService.updateUser(individualDto);
        individualDto.setUser(updatedUser);
        return individualDto;
    }
    
    public IndividualDto getIndividualByEmail(String email) {
        return personServiceClient.getPersonByEmail(email);
    }
} 