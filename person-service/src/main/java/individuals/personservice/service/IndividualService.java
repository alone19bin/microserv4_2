package individuals.personservice.service;






import individuals.common.dto.IndividualDto;
import individuals.personservice.model.Individual;
import individuals.personservice.model.User;
import individuals.personservice.repository.IndividualRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class IndividualService {

    private final IndividualRepository individualRepository;

    public Individual createIndividual(IndividualDto individualDto, User user) {
        Individual individual = Individual.builder()
                .user(user)
                .passportNumber(individualDto.getPassportNumber())
                .phoneNumber(individualDto.getPhoneNumber())
                .build();
        return individualRepository.save(individual);
    }

    public void deleteIndividual(UUID individualId) {
        Individual individual = individualRepository.findById(individualId)
                .orElseThrow(() -> new EntityNotFoundException("Individual not found"));
        individualRepository.delete(individual);
    }
}