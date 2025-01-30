package individuals.api.controller;

import individuals.common.dto.IndividualDto;

import individuals.personservice.service.IndividualService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Data
@RequestMapping("/api/individuals")
public class IndividualController {
    private final IndividualService individualService;

    @PostMapping
    public ResponseEntity<IndividualDto> createIndividual(
            @RequestParam UUID userId,
            @Valid @RequestBody IndividualDto individualDto
    ) {
        return ResponseEntity.ok(individualService.createIndividual(userId, individualDto));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteIndividualByUserId(@PathVariable UUID userId) {
        individualService.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }
}