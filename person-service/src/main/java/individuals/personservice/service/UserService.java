package individuals.personservice.service;


import individuals.common.dto.UserDto;
import individuals.personservice.model.User;
import individuals.personservice.repository.UserRepository;
import individuals.personservice.exception.UserNotFoundException;
import individuals.personservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;;

    @Transactional(readOnly = true)
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return userMapper.toDto(user);
    }

    @Transactional
    public User createUser(UserDto userDto) {
        //Преобразуем DTO в сущность
        User user = userMapper.toEntity(userDto);

        //Сохраняем и возвращаем сущность
        return userRepository.save(user);
    }
    public UserDto createUserAndReturnDto(UserDto userDto) {
        User createdUser = createUser(userDto);
        return userMapper.toDto(createdUser);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}