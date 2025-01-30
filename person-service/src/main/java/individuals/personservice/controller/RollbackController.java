package individuals.personservice.controller;

import individuals.personservice.service.IndividualService;
import individuals.personservice.service.UserService;
import lombok.extern.log4j.Log4j;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rollback")
public class RollbackController {
    private final UserService userService;
    private final IndividualService individualService;

    @Transactional
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> rollbackUserCreation(@PathVariable UUID userId) {
        try {
            individualService.deleteByUserId(userId);
            userService.deleteById(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Rollback failed for user {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}