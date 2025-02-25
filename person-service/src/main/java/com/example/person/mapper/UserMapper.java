package com.example.person.mapper;

import com.example.dto.UserDto;
import com.example.person.entity.User;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final AddressMapper addressMapper;
    
    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        user.setId(dto.getId());
        user.setSecretKey(dto.getSecretKey());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setFilled(dto.isFilled());
        user.setAddress(addressMapper.toEntity(dto.getAddress()));
        
        if (dto.getCreated() == null) {
            user.setCreated(LocalDateTime.now());
        } else {
            user.setCreated(dto.getCreated());
        }
        
        user.setUpdated(LocalDateTime.now());
        
        return user;
    }
    
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setSecretKey(entity.getSecretKey());
        dto.setEmail(entity.getEmail());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setFilled(entity.getFilled() != null && entity.getFilled());
        dto.setAddress(addressMapper.toDto(entity.getAddress()));
        
        return dto;
    }
} 