package individuals.api.controller;

import individuals.api.dto.IndividualDto;

import individuals.api.service.IndividualService;
import individuals.api.service.PersonServiceClient;
import individuals.api.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Individual;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/individuals")
@RequiredArgsConstructor
public class IndividualsApiController {
    private final IndividualService individualService;
    private final PersonServiceClient personServiceClient;
    private final KeycloakService keycloakClient;

    @PostMapping("/register")
    public ResponseEntity<String> registerIndividual(@RequestBody IndividualDto individualDto) {
        try {
            // 1. Создание individual в individuals-api
            Individual createdIndividual = individualService.createIndividual(individualDto);

            // 2. Сохранение в Person Service
            String userId = personServiceClient.saveIndividual(createdIndividual);

            try {
                // 3. Регистрация в Keycloak
                keycloakClient.registerUser(userId, individualDto);
                return ResponseEntity.ok(userId);
            } catch (Exception keycloakError) {
                // Если Keycloak не удался - откатываем данные в Person Service
                personServiceClient.rollbackIndividual(userId);
                throw keycloakError;
            }
        } catch (Exception e) {
            log.error("Registration failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + e.getMessage());
        }
    }
}