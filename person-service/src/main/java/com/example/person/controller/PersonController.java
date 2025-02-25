package com.example.person.controller;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import com.example.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    public ResponseEntity<UserDto> createPerson(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(personService.createPerson(userDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID userId) {
        personService.deletePerson(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UserDto> updatePerson(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(personService.updatePerson(userDto));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<IndividualDto> getPersonByEmail(@PathVariable String email) {
        return ResponseEntity.ok(personService.getPersonByEmail(email));
    }
} 