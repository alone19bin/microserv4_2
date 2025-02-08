package individuals.api.controller;

import individuals.common.dto.IndividualDto;
import individuals.personservice.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class IndividualController {

    @Autowired
    private IndividualService individualService;

    @PostMapping
    public ResponseEntity<IndividualDto> createUser(@RequestBody IndividualDto individualDto) {
        IndividualDto createdIndividual = individualService.createIndividual(individualDto);
        return ResponseEntity.ok(createdIndividual);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        individualService.deleteIndividual(userId);
        return ResponseEntity.noContent().build();
    }
}