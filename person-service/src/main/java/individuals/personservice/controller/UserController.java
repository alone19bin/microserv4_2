package individuals.api.controller;

import individuals.api.service.UserService;
import individuals.common.dto.UserDto;
import individuals.api.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

            //сздание нового пользователя
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserDto userDto) {
        UserEntity createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //обновление информации о пользователе
    @PutMapping("/{userId}")
    public ResponseEntity<UserEntity> updateUser(
        @PathVariable UUID userId,
        @RequestBody UserDto userDto
    ) {
        UserEntity updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }
}