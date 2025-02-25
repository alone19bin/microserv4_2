package com.example.person.mapper;

import com.example.dto.IndividualDto;
import com.example.person.entity.Individual;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class IndividualMapper {
    private final UserMapper userMapper;
    
    public Individual toEntity(IndividualDto dto) {
        if (dto == null) {
            return null;
        }
        
        Individual individual = new Individual();
        individual.setId(dto.getId());
        individual.setPassportNumber(dto.getPassportNumber());
        individual.setPhoneNumber(dto.getPhoneNumber());
        individual.setEmail(dto.getEmail());
        individual.setVerifiedAt(dto.getVerifiedAt());
        individual.setArchivedAt(dto.getArchivedAt());
        individual.setStatus(dto.getStatus());
        individual.setUser(userMapper.toEntity(dto.getUser()));
        
        return individual;
    }
    
    public IndividualDto toDto(Individual entity) {
        if (entity == null) {
            return null;
        }
        
        IndividualDto dto = new IndividualDto();
        dto.setId(entity.getId());
        dto.setPassportNumber(entity.getPassportNumber());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setVerifiedAt(entity.getVerifiedAt());
        dto.setArchivedAt(entity.getArchivedAt());
        dto.setStatus(entity.getStatus());
        dto.setUser(userMapper.toDto(entity.getUser()));
        
        return dto;
    }
} 