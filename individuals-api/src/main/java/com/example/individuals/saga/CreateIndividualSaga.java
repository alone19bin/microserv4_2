package com.example.individuals.saga;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import com.example.individuals.client.PersonServiceClient;
import com.example.individuals.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateIndividualSaga {
    private final PersonServiceClient personServiceClient;
    private final KeycloakService keycloakService;
    
             //для тестирования-если true, то ошибки keycloak будут игнорироваться
    private boolean ignoreKeycloakErrors = true;

    public IndividualDto execute(IndividualDto individualDto) {
        UserDto createdUser = null;
        try {
                //существует ли пользователь с таким email
            try {
                IndividualDto existingIndividual = personServiceClient.getPersonByEmail(individualDto.getUser().getEmail());
                if (existingIndividual != null) {
                    log.info("Пользователь с email {} уже существует, обновляем данные", individualDto.getUser().getEmail());
                        //если пользователь существует, обновим его данные
                    individualDto.getUser().setId(existingIndividual.getUser().getId());
                    return updateExistingIndividual(individualDto);
                }
            } catch (Exception e) {
                    //сли  не найден, продолжаем создавать
                log.info("Пользователь с email {} не найден, создаем нового", individualDto.getUser().getEmail());
            }
            
                // 1: Создание пользователя в personservice
            createdUser = personServiceClient.createPerson(individualDto.getUser());
            individualDto.setUser(createdUser);
            
                 // создание пользователя в keycloak
            try {
                keycloakService.createUser(individualDto);
            } catch (Exception e) {
                log.warn("Ошибка при создании пользователя в Keycloak: {}", e.getMessage());
                           //если флаг ignoreKeycloakErrors = false, выбрасырасываем исключение дальше
                if (!ignoreKeycloakErrors) {
                    throw e;
                }
                   //иначе продолжаем выполнение,так как пользователь уже создан в person  service
            }
            
            return individualDto;
        } catch (Exception e) {
            log.error("Ошибка при создании пользователя: {}", e.getMessage(), e);
                        //если случае ошибки - откаты изменений
            if (createdUser != null) {
                try {
                    personServiceClient.deletePerson(createdUser.getId());
                } catch (Exception rollbackEx) {
                    log.error("Ошиб ка при откате изменений: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            throw new RuntimeException("Ошибка при создании пользователя: " + e.getMessage(), e);
        }
    }
    
    private IndividualDto updateExistingIndividual(IndividualDto individualDto) {
           //обновление данные в personservice
        UserDto updatedUser = personServiceClient.updatePerson(individualDto.getUser());
        individualDto.setUser(updatedUser);
        
        //бновляние данных в keycloak
        keycloakService.updateUser(individualDto);
        
        return individualDto;
    }
    
            //  для тестирования - устанавливает флаг ignoreKeycloakErrors
    public void setIgnoreKeycloakErrors(boolean ignoreKeycloakErrors) {
        this.ignoreKeycloakErrors = ignoreKeycloakErrors;
    }
} 