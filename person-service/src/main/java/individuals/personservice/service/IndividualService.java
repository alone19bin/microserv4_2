package individuals.personservice.service;
import individuals.common.dto.IndividualDto;
import individuals.personservice.model.Individual;
import individuals.personservice.model.User;
import individuals.personservice.repository.IndividualRepository;
import individuals.personservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndividualService {
    private final IndividualRepository individualRepository;
    private final UserRepository userRepository;

    public Individual createIndividual(IndividualDto individualDto) {
        User user = createUserFromDto(individualDto);
        userRepository.save(user);

        Individual individual = new Individual();
        individual.setUser(user);
        individual.setPassportNumber(individualDto.getPassportNumber());
        individual.setPhoneNumber(individualDto.getPhoneNumber());

        return individualRepository.save(individual);
    }

    private User createUserFromDto(IndividualDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        return user;
    }
}