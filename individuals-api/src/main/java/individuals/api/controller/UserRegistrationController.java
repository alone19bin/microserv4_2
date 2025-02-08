package individuals.api.controller;

import individuals.api.saga.UserRegistrationSagaResult;
import individuals.api.saga.SagaStatus;
import individuals.common.dto.UserRegistrationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static individuals.api.saga.SagaStatus.COMPLETED;

@Slf4j
@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class UserRegistrationController {
    private final UserRegistrationSagaOrchestrator orchestrator;

    @PostMapping
    public ResponseEntity<?> registerUser(
            @RequestBody @Valid UserRegistrationRequest request
    ) {
        try {
            UserRegistrationSagaResult result = orchestrator.execute(request);

            return result.getStatus() == COMPLETED
                    ? ResponseEntity.ok(result.getResponse())
                    : ResponseEntity.badRequest().body(result.getErrorMessage());
        } catch (Exception e) {
            log.error("Оши бка регистрации пользователя", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("  Ошибка при регистрации");
        }
    }
}