/*
package test;



import individuals.common.dto.IndividualDto;

import individuals.personservice.model.User;
import individuals.personservice.repository.UserRepository;
import individuals.personservice.service.UserService;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private IndividualDto individualDto;
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
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(individualDto);

        assertNotNull(createdUser);
        assertEquals(user.getId(), createdUser.getId());
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getFirstName(), createdUser.getFirstName());
        assertEquals(user.getLastName(), createdUser.getLastName());
        assertEquals(user.getCreated(), createdUser.getCreated());
        assertEquals(user.getUpdated(), createdUser.getUpdated());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        UUID userId = UUID.randomUUID();

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}*/
