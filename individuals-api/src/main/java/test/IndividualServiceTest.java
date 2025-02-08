/*
package test;



import individuals.common.dto.IndividualDto;

import individuals.personservice.mapper.IndividualMapper;
import individuals.personservice.model.Individual;
import individuals.personservice.model.User;
import individuals.personservice.repository.IndividualRepository;
import individuals.personservice.repository.UserRepository;
import individuals.personservice.service.IndividualService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IndividualServiceTest {

    @Mock
    private IndividualRepository individualRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IndividualMapper individualMapper;

    @InjectMocks
    private IndividualService individualService;

    private IndividualDto individualDto;
    private Individual individual;
    private User user;

    @BeforeEach
    public void setUp() {
        UUID userId = UUID.randomUUID();
        individualDto = IndividualDto.builder()
                .userId(userId)
                .passportNumber("1234567890")
                .phoneNumber("+1234567890")
                .email("user@example.com")
                .firstName("John")
                .lastName("Doe")
                .verifiedAt(LocalDateTime.now())
                .status("ACTIVE")
                .build();

        user = User.builder()
                .id(userId)
                .email("user@example.com")
                .firstName("John")
                .lastName("Doe")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();

        individual = Individual.builder()
                .id(userId)
                .user(user)
                .passportNumber("1234567890")
                .phoneNumber("+1234567890")
                .email("user@example.com")
                .verifiedAt(LocalDateTime.now())
                .archivedAt(null)
                .status("ACTIVE")
                .build();
    }

    @Test
    public void testCreateIndividual() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(individualRepository.save(any(Individual.class))).thenReturn(individual);
        when(individualMapper.toDto(individual)).thenReturn(individualDto);

        IndividualDto createdDto = individualService.createIndividual(individualDto);

        assertNotNull(createdDto);
        assertEquals(individualDto.getUserId(), createdDto.getUserId());
        assertEquals(individualDto.getPassportNumber(), createdDto.getPassportNumber());
        assertEquals(individualDto.getPhoneNumber(), createdDto.getPhoneNumber());
        assertEquals(individualDto.getEmail(), createdDto.getEmail());
        assertEquals(individualDto.getFirstName(), createdDto.getFirstName());
        assertEquals(individualDto.getLastName(), createdDto.getLastName());
        assertEquals(individualDto.getVerifiedAt(), createdDto.getVerifiedAt());
        assertEquals(individualDto.getStatus(), createdDto.getStatus());

        verify(userRepository, times(1)).save(any(User.class));
        verify(individualRepository, times(1)).save(any(Individual.class));
        verify(individualMapper, times(1)).toDto(individual);
    }

    @Test
    public void testDeleteIndividual() {
        UUID userId = UUID.randomUUID();

        individualService.deleteIndividual(userId);

        verify(individualRepository, times(1)).deleteById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }
}*/
