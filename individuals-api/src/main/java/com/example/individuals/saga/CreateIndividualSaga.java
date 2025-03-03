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
    
        // для тестирования - если true, то ошибки keycloak будут игнорироваться
    private boolean ignoreKeycloakErrors = true;

    public IndividualDto execute(IndividualDto individualDto) {
        UserDto createdUser = null;
        try {
               //существует ли пользователь с таким email
            try {
                IndividualDto existingIndividual = personServiceClient.getPersonByEmail(individualDto.getUser().getEmail());
                if (existingIndividual != null) {
                    log.info("Пользователь с email {} уже существует, обновляем данные", individualDto.getUser().getEmail());
                       //Если пользователь существует, обновляем его данные
                    individualDto.getUser().setId(existingIndividual.getUser().getId());
                    return updateExistingIndividual(individualDto);
                }
            } catch (Exception e) {
                //Если пользователь не найден, продолжаем создание
                log.info("Пользователь с email {} не найден, создаем нового", individualDto.getUser().getEmail());
            }

                 // 1: Создание пользователя в personservice
            createdUser = personServiceClient.createPerson(individualDto.getUser());
            individualDto.setUser(createdUser);

                 // 2: Создание пользователя в keycloak
            try {
                keycloakService.createUser(individualDto);
            } catch (Exception e) {
                log.warn("Ошибка при создании пользователя в Keycloak: {}", e.getMessage());
                // Если  ignoreKeycloakErrors = false, пробрасываем исключение дальше
                if (!ignoreKeycloakErrors) {
                    throw e;
                }
                //иначе - продолжаем выполнение, так как пользователь уже создан в person service
            }

            return individualDto;
        } catch (Exception e) {
            log.error("Ошибка при создании пользователя: {}", e.getMessage(), e);
                        // В случае ошибки - откатываем изменения
            if (createdUser != null) {
                try {
                    personServiceClient.deletePerson(createdUser.getId());
                } catch (Exception rollbackEx) {
                    log.error("Ошибка при откате изменений: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            throw new RuntimeException("Ошибка при создании пользователя: " + e.getMessage(), e);
        }
    }
    
    private IndividualDto updateExistingIndividual(IndividualDto individualDto) {
        UserDto originalUser = null;
        UserDto updatedUser = null;
        
        try {
            //Сохранить оригинальные данные пользователя для возможного отката
            originalUser = copyUserDto(individualDto.getUser());
            
               // Обновить данные в personservice
            updatedUser = personServiceClient.updatePerson(individualDto.getUser());
            individualDto.setUser(updatedUser);
            
              // Обновить данные в keycloak
            try {
                keycloakService.updateUser(individualDto);
            } catch (Exception e) {
                log.warn("Ошибка при обновлении пользователя в Keycloak: {}", e.getMessage());
                //Если  ignoreKeycloakErrors = false, выполняем откат и пробрасываем исключение
                if (!ignoreKeycloakErrors) {
                    // Откат изменений в personservice
                    try {
                        personServiceClient.updatePerson(originalUser);
                    } catch (Exception rollbackEx) {
                        log.error("Ошибка при откате изменений: {}", rollbackEx.getMessage(), rollbackEx);
                        throw new RuntimeException("Ошибка при откате изменений: " + rollbackEx.getMessage(), rollbackEx);
                    }
                    throw e;
                }
            }
            
            return individualDto;
        } catch (Exception e) {
            log.error("Ошибка при обновлении пользователя: {}", e.getMessage(), e);
               //В случае ошибки - откатываем изменения
            if (updatedUser != null && originalUser != null) {
                try {
                    //Возвращаем оригинальные данные пользователя?
                    personServiceClient.updatePerson(originalUser);
                } catch (Exception rollbackEx) {
                    log.error("Ошибка при откате изменений: {}", rollbackEx.getMessage(), rollbackEx);
                }
            }
            throw new RuntimeException("Ошибка при обновлении пользователя: " + e.getMessage(), e);
        }
    }
    
      // доп метод для создания копии объекта UserDto
    private UserDto copyUserDto(UserDto source) {
        if (source == null) {
            return null;
        }
        UserDto copy = new UserDto();
        copy.setId(source.getId());
        copy.setEmail(source.getEmail());
        copy.setFirstName(source.getFirstName());
        copy.setLastName(source.getLastName());
        //Копируем другие поля, если они есть
        return copy;
    }
    
        // для тестирования  устанавливааем  флаг ignoreKeycloakErrors
    public void setIgnoreKeycloakErrors(boolean ignoreKeycloakErrors) {
        this.ignoreKeycloakErrors = ignoreKeycloakErrors;
    }
} 