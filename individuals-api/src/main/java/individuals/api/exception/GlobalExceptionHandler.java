package individuals.api.exception;

import individuals.personservice.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        log.error("User not found: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("USER_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(KeycloakOperationException.class)
    public ResponseEntity<ErrorResponse> handleKeycloakError(KeycloakOperationException ex) {
        log.error("Keycloak operation failed: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("KEYCLOAK_ERROR", ex.getMessage()));
    }
}