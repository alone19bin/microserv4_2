package individuals.api.controller;

import individuals.personservice.client.PersonServiceClient;
import individuals.api.keyloack.KeycloakService;
import individuals.api.saga.SagaStatus;
import individuals.api.saga.UserRegistrationResponse;
import individuals.api.saga.UserRegistrationSagaResult;
import individuals.common.dto.IndividualDto;
import individuals.common.dto.UserDto;
import individuals.common.dto.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.UUID;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationSagaOrchestrator {
    private final PersonServiceClient personServiceClient;
    private final KeycloakService keycloakService;

    public UserRegistrationSagaResult execute(UserRegistrationRequest request) {
        try {
            UserDto createdUser = personServiceClient.createUser(request.getUser());

            IndividualDto individualToCreate = request.getIndividual();
            individualToCreate.setUserId(UUID.fromString(createdUser.getId().toString()));

            IndividualDto createdIndividual = personServiceClient.createIndividual(individualToCreate);

            String keycloakUserId = keycloakService.registerUser(
                    createdUser,
                    generateTemporaryPassword()
            );

            return UserRegistrationSagaResult.builder()
                    .status(SagaStatus.COMPLETED)
                    .response(UserRegistrationResponse.builder()
                            .userId(createdUser.getId())
                            .email(createdUser.getEmail())
                            .build())
                    .build();

        } catch (Exception e) {
            log.error("Ошибка в SAGA процессе", e);
            return UserRegistrationSagaResult.builder()
                    .status(SagaStatus.FAILED)
                    .errorMessage(e.getMessage())
                    .build();
        }
    }

    private String generateTemporaryPassword() {
        return "TempPass123!";
    }
}