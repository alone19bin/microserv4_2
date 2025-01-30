package individuals.api.controller;

import individuals.api.saga.UserRegistrationSagaOrchestrator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static individuals.api.saga.SagaStatus.COMPLETED;
import static individuals.api.saga.SagaStatus.FAILED;

@RestController
@RequestMapping("/api/registration")
public class UserRegistrationController {
    private final UserRegistrationSagaOrchestrator sagaOrchestrator;

    @PostMapping
    public ResponseEntity<UserRegistrationResponse> registerUser(
            @RequestBody @Valid UserRegistrationRequest request
    ) {
        SagaResult<UserRegistrationResponse> result =
                sagaOrchestrator.execute(request);

        return switch (result.getStatus()) {
            case COMPLETED -> ResponseEntity.ok(result.getResult());
            case FAILED -> ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(buildErrorResponse(result));
        };
    }
}