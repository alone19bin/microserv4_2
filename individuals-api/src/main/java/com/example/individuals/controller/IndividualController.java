package com.example.individuals.controller;

import com.example.dto.IndividualDto;
import com.example.individuals.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/individuals")
@RequiredArgsConstructor
@Slf4j
public class IndividualController {
    private final IndividualService individualService;

    @PostMapping
    public ResponseEntity<?> createIndividual(@RequestBody IndividualDto individualDto) {
        try {
            IndividualDto createdIndividual = individualService.createIndividual(individualDto);
            return ResponseEntity.ok(createdIndividual);
        } catch (Exception e) {
            log.error("Ошибкапри создании пользователя: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при создании пользователя: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteIndividual(@RequestBody IndividualDto individualDto) {
        try {
            individualService.deleteIndividual(individualDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошиб ка при удалении пользователя: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateIndividual(@RequestBody IndividualDto individualDto) {
        try {
            IndividualDto updatedIndividual = individualService.updateIndividual(individualDto);
            return ResponseEntity.ok(updatedIndividual);
        } catch (Exception e) {
            log.error(" Ошибка при обновлении пользователя: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при обновлении пользователя: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getIndividualByEmail(@RequestParam String email) {
        try {
            IndividualDto individual = individualService.getIndividualByEmail(email);
            return ResponseEntity.ok(individual);
        } catch (Exception e) {
            log.error("Ошибка при получении пользователя: {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при получении пользователя: " + e.getMessage());
        }
    }



    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteIndividualByEmail(@PathVariable String email) {
        try {
                    //Сначала получаем пользователя по email
            IndividualDto individual = individualService.getIndividualByEmail(email);
            
                //потом удаляем
            individualService.deleteIndividual(individual);
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошибка при удалении пользователя по email {}: {}", email, e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIndividualById(@PathVariable UUID id) {
        try {
            individualService.deleteIndividualById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошибка при удалении пользователя по ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

} 