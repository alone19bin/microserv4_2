package individuals.api.service;

import individuals.api.entity.UserEntity;
import individuals.api.entity.UserHistoryEntity;
import individuals.api.repository.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;

    @Transactional
    public void logUserCreation(UserEntity user) {
        UserHistoryEntity history = createHistoryEntry(user, "USER_CREATION", "Создание нового пользователя");
        userHistoryRepository.save(history);
    }

    @Transactional
    public void logUserUpdate(UserEntity user, String changes) {
        UserHistoryEntity history = createHistoryEntry(user, "USER_UPDATE", "Обновление данных пользователя");
        history.setChangedValues(changes);
        userHistoryRepository.save(history);
    }

               //Добавленные методы
    @Transactional(readOnly = true)
    public List<UserHistoryEntity> getUserHistory(UUID userId) {
        return userHistoryRepository.findByUser_Id(userId);
    }

    @Transactional(readOnly = true)
    public List<UserHistoryEntity> getHistoryByUserType(String userType) {
        return userHistoryRepository.findByUserType(userType);
    }

    private UserHistoryEntity createHistoryEntry(UserEntity user, String type, String reason) {
        UserHistoryEntity history = new UserHistoryEntity();
        history.setUser(user);
        history.setUserType(type);
        history.setReason(reason);
        history.setCreated(LocalDateTime.now());
        return history;
    }
}