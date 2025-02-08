package individuals.personservice.service;

import individuals.common.dto.IndividualDto;
import individuals.common.dto.UserDto;
import individuals.personservice.entity.Address;
import individuals.personservice.entity.Individual;
import individuals.personservice.entity.User;
import individuals.personservice.mapper.AddressMapper;
import individuals.personservice.mapper.IndividualMapper;
import individuals.personservice.mapper.UserMapper;
import individuals.personservice.repository.AddressRepository;
import individuals.personservice.repository.IndividualRepository;
import individuals.personservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class IndividualService {
    private final IndividualRepository individualRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;
    private final IndividualMapper individualMapper;
    private final AddressMapper addressMapper;

    public IndividualDto createIndividual(IndividualDto individualDto) {
                        //роль пользователя
        individualDto.getUser().setRole(UserDto.Role.USER);

        Address address = addressMapper.toEntity(individualDto.getUser().getAddress());
        addressRepository.save(address);

        User user = userMapper.toEntity(individualDto.getUser());
        user.setAddress(address);
        userRepository.save(user);

        Individual individual = individualMapper.toEntity(individualDto);
        individual.setUser(user);
        individual.setVerifiedAt(LocalDateTime.now());
        individualRepository.save(individual);

        return individualMapper.toDto(individual);
    }

    //
    public void deleteIndividual(UUID userId) {
        Individual individual = individualRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Individual not found"));

        individualRepository.delete(individual);
    }
}