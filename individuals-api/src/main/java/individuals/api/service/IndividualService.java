package individuals.api.service;

import individuals.api.entity.IndividualEntity;
import individuals.api.entity.UserEntity;
import individuals.api.mapper.IndividualMapper;
import individuals.api.mapper.UserMapper;
import individuals.api.repository.IndividualRepository;
import individuals.api.repository.UserRepository;
import individuals.common.dto.IndividualDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndividualService {
    private final IndividualRepository individualRepository;
    private final UserRepository userRepository;
    private final IndividualMapper individualMapper;
    private final UserMapper userMapper;

    public IndividualEntity createIndividual(IndividualDto individualDto) {
        UserEntity user = createUserFromDto(individualDto);
        userRepository.save(user);

        IndividualEntity individual = individualMapper.toEntity(individualDto);
        individual.setUserId(user.getId());
        return individualRepository.save(individual);
    }

    private UserEntity createUserFromDto(IndividualDto dto) {
        return userMapper.toEntity(dto.getUser());
    }
}