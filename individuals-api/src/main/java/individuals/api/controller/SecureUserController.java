package individuals.api.controller;

import individuals.common.dto.UserDto;

import individuals.personservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
@Slf4j
@RequiredArgsConstructor
public class SecureUserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserDto> getCurrentUser(Authentication auth) {
        UserDto userDto = userService.findUserByEmail(auth.getName());
        return ResponseEntity.ok(userDto);
    }
}