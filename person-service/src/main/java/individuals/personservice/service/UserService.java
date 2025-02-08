package individuals.personservice.service;


import individuals.common.dto.IndividualDto;
import individuals.common.dto.UserDto;
import individuals.personservice.client.PersonServiceClient;
import individuals.personservice.entity.Individual;
import individuals.personservice.exception.UserNotFoundException;
import individuals.personservice.repository.AddressRepository;
import individuals.personservice.repository.IndividualRepository;
import individuals.personservice.repository.UserRepository;
import individuals.personservice.entity.User;
import individuals.personservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final IndividualRepository individualRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    public void deleteUser(UUID userId) {
        Individual individual = individualRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        individualRepository.delete(individual);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getAddress() != null) {
            addressRepository.delete(user.getAddress());
        }

        userRepository.delete(user);
    }

    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public UserDto createUser(IndividualDto individualDto) {
                    // создания пользователя из Individual dto
        User user = userMapper.toEntity(individualDto.getUser());
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }


}