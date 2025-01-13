package individuals.api.controller;

import individuals.api.entity.UserHistoryEntity;
import individuals.api.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-history")
@RequiredArgsConstructor
public class UserHistoryController {
    private final UserHistoryService userHistoryService;

    //получение полной истории изменений для пользователя
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserHistoryEntity>> getUserHistory(@PathVariable UUID userId) {
        List<UserHistoryEntity> history = userHistoryService.getUserHistory(userId);
        return ResponseEntity.ok(history);
    }

                //получение истории по типу пользователя
    @GetMapping("/type/{userType}")
    public ResponseEntity<List<UserHistoryEntity>> getHistoryByUserType(@PathVariable String userType) {
        List<UserHistoryEntity> history = userHistoryService.getHistoryByUserType(userType);
        return ResponseEntity.ok(history);
    }
}