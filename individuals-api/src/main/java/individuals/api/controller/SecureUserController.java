package individuals.api.controller;

import individuals.common.dto.UserDto;
import individuals.personservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SecureUserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(Authentication auth) {
        UserDto userDto = userService.getUserByEmail(auth.getName());
        return ResponseEntity.ok(userDto);
    }
}