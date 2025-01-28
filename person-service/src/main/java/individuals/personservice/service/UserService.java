package individuals.api.service;


import individuals.api.entity.UserEntity;
import individuals.api.repository.UserRepository;
import individuals.common.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;  // Добавлен импорт UUID

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Transactional
    public UserEntity createUser(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setFilled(false);

        return userRepository.save(user);
    }

    @Transactional
    public UserEntity updateUser(UUID userId, UserDto userDto) {
        UserEntity existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setUpdated(LocalDateTime.now());
        existingUser.setFilled(true);

        return userRepository.save(existingUser);
    }
}