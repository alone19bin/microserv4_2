package individuals.personservice.controller;

import individuals.personservice.service.IndividualService;
import individuals.personservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/rollback")
@RequiredArgsConstructor
public class RollbackController {
    private final IndividualService individualService;
    private final UserService userService;

    @DeleteMapping("/{userId}")
    public void rollbackUser(@PathVariable UUID userId) {
        individualService.deleteIndividual(userId);
        userService.deleteUser(userId);
    }
}