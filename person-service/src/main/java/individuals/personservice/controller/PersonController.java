package individuals.personservice.controller;

import individuals.common.dto.PersonDto;
import individuals.personservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



// обрабатываем HTTPзапрос для связи с персональными  данными
@RestController
@RequestMapping("/api/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;
                // эндпоинты  с персональными данными
    @PostMapping("/save")
    public ResponseEntity<String> savePerson(@RequestBody PersonDto personDto) {
        String userId = personService.savePerson(personDto);
        return ResponseEntity.ok(userId);
    }

    @DeleteMapping("/rollback/{userId}")
    public ResponseEntity<Void> rollbackPerson(@PathVariable String userId) {
        personService.deletePerson(userId);
        return ResponseEntity.noContent().build();
    }
}
