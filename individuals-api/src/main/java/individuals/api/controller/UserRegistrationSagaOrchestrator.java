package individuals.api.controller;

import individuals.api.exception.ErrorResponse;
import individuals.api.keyloack.KeycloakService;
import individuals.api.saga.SagaContext;
import individuals.api.saga.SagaStatus;
import individuals.api.saga.UserRegistrationSagaResult;

import individuals.common.dto.IndividualDto;
import individuals.common.dto.UserDto;
import individuals.common.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/registration")
@Slf4j
public class UserRegistrationSagaOrchestrator {

    private final PersonServiceClient personServiceClient;
    private final KeycloakService keycloakService;
    private final int maxRetryAttempts;
    private final long backoffDelay;

    public UserRegistrationSagaOrchestrator(
            PersonServiceClient personServiceClient,
            KeycloakService keycloakService
    ) {
        this.personServiceClient = personServiceClient;
        this.keycloakService = keycloakService;
        this.maxRetryAttempts = 3; // Можно вынести в application.properties
        this.backoffDelay = 1000; // В миллисекундах
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        UserRegistrationSagaResult result = orchestrateRegistration(request);
        if (result.getStatus() == SagaStatus.COMPLETED) {
            return ResponseEntity.ok(result);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Ошибка  регистрации",
                    result.getErrorMessage(),
                    "REGISTRATION_FAILED",
                    "/api/registration"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    public UserRegistrationSagaResult orchestrateRegistration(UserRegistrationRequest request) {
        UUID transactionId = UUID.randomUUID();
        SagaContext context = new SagaContext(transactionId);

        try {
            // Шаг1: Создание пользователя в person-service
            UserDto createdUser = executeWithRetry(() ->
                            personServiceClient.createUser(request.toUserDTO()),
                    " Созданиепользователя", context);

                     // Шаг 2: Создание individual
            IndividualDto createdIndividual = executeWithRetry(() ->
                            personServiceClient.createIndividual(createdUser.getId(), request.toIndividualDTO()),
                    "Создание individual", context);

            //шаг 3: Регистрация в Keycloak
            executeWithRetry(() ->
                            keycloakService.registerUser(createdUser),
                    "Регистрация  в  Keycloak", context);

            return UserRegistrationSagaResult.builder()
                    .status(SagaStatus.COMPLETED)
                    .userId(createdUser.getId())
                    .individualId(createdIndividual.getId())
                    .build();
        } catch (Exception e) {
            compensateTransaction(context);
            return UserRegistrationSagaResult.builder()
                    .status(SagaStatus.FAILED)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }

    private <T> T executeWithRetry(Supplier<T> action, String stepName, SagaContext context) {
        int attempts = 0;
        while (attempts < maxRetryAttempts) {
            try {
                T result = action.get();
                context.addCompletedStep(stepName);
                return result;
            } catch (Exception e) {
                attempts++;
                log.warn("Ошибка на шаге {}, попытка {} из {}", stepName, attempts, maxRetryAttempts);
                if (attempts >= maxRetryAttempts) {
                    throw new RuntimeException("Неудалось выполнить шаг: " + stepName, e);
                }
                try {
                    Thread.sleep(backoffDelay * attempts);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new RuntimeException("Пр евышено количество попыток для шага: " + stepName);
    }

    private void compensateTransaction(SagaContext context) {
        log.error("Начало компенсационных действий для транзакции {}", context.getTransactionId());
        List<String> completedSteps = context.getCompletedSteps();
        Collections.reverse(completedSteps);

        for (String step : completedSteps) {
            try {
                switch (step) {
                    case "  Создание пользователя" ->
                            personServiceClient.rollbackUserCreation(context.getUserId());
                    case "Создание individual" ->
                            personServiceClient.rollbackIndividualCreation(context.getUserId());
                    case "Регистрация в Keycloak" ->
                            keycloakService.deleteUser(context.getUserId());
                }
            } catch (Exception e) {
                log.error("Ошибка компенсации шага {}", step, e);
            }
        }
    }
}