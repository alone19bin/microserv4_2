package individuals.personservice.service;

import individuals.common.dto.PersonDto;
import individuals.personservice.model.Person;
import individuals.personservice.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional
    public String savePerson(PersonDto personDto) {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setEmail(personDto.getEmail());

        personRepository.save(person);
        return person.getId();
    }

    @Transactional
    public void deletePerson(String userId) {
        personRepository.deleteById(userId);
    }
}
