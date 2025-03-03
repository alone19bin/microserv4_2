package com.example.person.service;

import com.example.dto.IndividualDto;
import com.example.dto.UserDto;
import com.example.person.entity.Individual;
import com.example.person.entity.User;
import com.example.person.mapper.IndividualMapper;
import com.example.person.mapper.UserMapper;
import com.example.person.repository.IndividualRepository;
import com.example.person.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IndividualRepository individualRepository;
    private final IndividualMapper individualMapper;

    @Transactional
    public UserDto createPerson(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

          //ненерирация ID, если нет
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }

          // инициализируем даты
        LocalDateTime now = LocalDateTime.now();
        user.setCreated(now);
        user.setUpdated(now);
        
        if (user.getAddress() != null) {
              //ID для адреса, если он не указан
            if (user.getAddress().getId() == null) {
                user.getAddress().setId(UUID.randomUUID());
            }
            
            user.getAddress().setCreated(now);
            user.getAddress().setUpdated(now);
            user.getAddress().setArchived(now);
            
            if (user.getAddress().getCountry() != null) {
                //для country используется integer id, поэтому не генерируем UUID?
                user.getAddress().getCountry().setCreated(now);
                user.getAddress().getCountry().setUpdated(now);
            }
        }

        // Сохранение
        user = userRepository.save(user);

           //создание запись в таблице individuals
        Individual individual = new Individual();
        individual.setId(UUID.randomUUID());
        individual.setUser(user);
        individual.setEmail(user.getEmail());
        individual.setStatus("ACTIVE");
        individual.setVerifiedAt(now);
        individual.setArchivedAt(now);

        individualRepository.save(individual);
        
        return userMapper.toDto(user);
    }

    @Transactional
    public void deletePerson(UUID userId) {
        // Сначала получаем email пользователя, которого удаляем
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        String email = user.getEmail();
        
        // Сначала удаляем запись из таблицы individuals для этого пользователя
        individualRepository.findByUserId(userId).ifPresent(individual -> 
            individualRepository.delete(individual)
        );
        
        // Удаляем указанного пользователя
        userRepository.deleteById(userId);
        
        // Находим и удаляем всех других пользователей с тем же email и их записи в individuals
        List<User> usersWithSameEmail = userRepository.findAllByEmail(email);
        for (User userWithSameEmail : usersWithSameEmail) {
            if (!userWithSameEmail.getId().equals(userId)) {
                // Сначала удаляем запись из individuals
                individualRepository.findByUserId(userWithSameEmail.getId()).ifPresent(individual -> 
                    individualRepository.delete(individual)
                );
                // Затем удаляем самого пользователя
                userRepository.deleteById(userWithSameEmail.getId());
            }
        }
    }

    @Transactional
    public UserDto updatePerson(UserDto userDto) {
        User existingUser;
        
          //Если унас  ID указан, ищем пользователя по ID
        if (userDto.getId() != null) {
            existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userDto.getId()));
        } else {
              //если ID не указан, ищем всех пользователей с таким email
            List<User> usersWithSameEmail = userRepository.findAllByEmail(userDto.getEmail());
            
            if (usersWithSameEmail.isEmpty()) {
                throw new RuntimeException("User not found with email: " + userDto.getEmail());
            } else if (usersWithSameEmail.size() > 1) {
                   //если найдено несколько пользователей с одиснаковым email, выбираем первого
                      // и логируем предупреждение
                log.warn("Fo und multiple users with email {}, using the first one", userDto.getEmail());
                existingUser = usersWithSameEmail.get(0);
            } else {
                existingUser = usersWithSameEmail.get(0);
            }
            
            //устанавка id найденного пользователя?
            userDto.setId(existingUser.getId());
        }
        
        User updatedUser = userMapper.toEntity(userDto);
        
        //Сохраняем дату
        updatedUser.setCreated(existingUser.getCreated());
        
                    //Обновляем дату
        LocalDateTime now = LocalDateTime.now();
        updatedUser.setUpdated(now);
        
           //ксли адрес не имеет id, используем id существующего адреса
        if (updatedUser.getAddress() != null && updatedUser.getAddress().getId() == null && existingUser.getAddress() != null) {
            updatedUser.getAddress().setId(existingUser.getAddress().getId());
            updatedUser.getAddress().setCreated(existingUser.getAddress().getCreated());
            updatedUser.getAddress().setUpdated(now);
        }
        
        //сзохраняем обновленного пользователя
        final User savedUser = userRepository.save(updatedUser);
        
           //обновляем запись в таблице individuals
        Individual individual = individualRepository.findByUserId(savedUser.getId())
                .orElseGet(() -> {
                    // Если запись не найдена, создаем новую
                    Individual newIndividual = new Individual();
                    newIndividual.setId(UUID.randomUUID());
                    newIndividual.setUser(savedUser);
                    newIndividual.setStatus("ACTIVE");
                    return newIndividual;
                });

        individual.setEmail(savedUser.getEmail());
        individual.setVerifiedAt(now);

        if (individual.getArchivedAt() == null) {
            individual.setArchivedAt(now);
        }
        

        individualRepository.save(individual);
        
        return userMapper.toDto(savedUser);
    }
    
    @Transactional(readOnly = true)
    public IndividualDto getPersonByEmail(String email) {
        //поиск всех пользователей с этим email
        List<User> usersWithSameEmail = userRepository.findAllByEmail(email);
        
        if (usersWithSameEmail.isEmpty()) {
            log.warn("User not found with email: {}", email);
            throw new RuntimeException("User not found with email: " + email);
        }
        
        User user;
        if (usersWithSameEmail.size() > 1) {
                    // Если найдено несколько пользователей с одинаковым email то  выбираем первого
                                             // и логируем предупреждение
            log.warn("Found multiple users with email {}, using the first one", email);
            user = usersWithSameEmail.get(0);
        } else {
            user = usersWithSameEmail.get(0);
        }
        
        // Ищем запись в таблице individuals
        Optional<Individual> individualOpt = individualRepository.findByUserId(user.getId());
        
        if (individualOpt.isEmpty()) {
            log.warn("Individual not found for user with email: {}, creating new one", email);
            
               //Если запись не найдена, создаем новую
            Individual individual = new Individual();
            individual.setId(UUID.randomUUID());
            individual.setUser(user);
            individual.setEmail(user.getEmail());
            individual.setStatus("ACTIVE");
            
            LocalDateTime now = LocalDateTime.now();
            individual.setVerifiedAt(now);
            individual.setArchivedAt(now);
            
                  // Сохра няем запись в таблице individuals
            individual = individualRepository.save(individual);
            
            return individualMapper.toDto(individual);
        }
        
        return individualMapper.toDto(individualOpt.get());
    }

    @Transactional
    public void deleteIndividual(UUID userId) {
        Individual individual = individualRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Individual not found for user with ID: " + userId));
        individualRepository.delete(individual);
    }
} 