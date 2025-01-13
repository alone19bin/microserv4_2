package individuals.api.saga;

import individuals.api.entity.UserEntity;
import individuals.api.mapper.AddressMapper;
import individuals.api.mapper.IndividualMapper;
import individuals.api.mapper.UserMapper;
import individuals.api.entity.AddressEntity;
import individuals.api.entity.IndividualEntity;
import individuals.api.service.UserService;
import individuals.common.dto.UserDto;
import individuals.common.dto.IndividualDto;
import individuals.common.dto.AddressDto;
import individuals.api.service.AddressService;
import individuals.api.service.IndividualService;
import individuals.api.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SagaOrchestrator {
    private final UserMapper userMapper;
    private final IndividualMapper individualMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final IndividualService individualService;
    private final UserService userService;
    private final KeycloakService keycloakService;

    @Transactional
    public UserEntity createUserWithIndividual(UserDto userDto) {
        try {
                 //1. Создаем адреса
            AddressEntity address = addressService.createAddress(userDto.getAddress());

            // 2. Создание индивидуалс
            IndividualEntity individual = individualService.createIndividual(userDto.getIndividual(), UUID.randomUUID());

            //   3.  Создание пользователя
            UserEntity user = userMapper.toEntity(userDto);
            user.setAddress(address);
            user.setIndividual(individual);

                    // 4. регистрация в Keycloak
            keycloakService.registerUser(user, userDto.getIndividual());

              // 5.  сохранение
            return userService.createUser(user);

        } catch (Exception e) {
            compensateUserCreation(e);
            throw new RuntimeException("Ошибка создания пользователя", e);
        }
    }

    private void compensateUserCreation(Exception e) {
        log.error("Компенсация транзакции: {}", e.getMessage());
        //откат транзакций
    }
}