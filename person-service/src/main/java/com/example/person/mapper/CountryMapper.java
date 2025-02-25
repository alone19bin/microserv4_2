package com.example.person.mapper;

import com.example.dto.CountryDto;
import com.example.person.entity.Country;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class CountryMapper {
    
    public Country toEntity(CountryDto dto) {
        if (dto == null) {
            return null;
        }
        
        Country country = new Country();
        country.setId(dto.getId());
        country.setName(dto.getName());
        country.setAlpha2(dto.getAlpha2());
        country.setAlpha3(dto.getAlpha3());
        country.setStatus(dto.getStatus());
        
        if (dto.getCreated() == null) {
            country.setCreated(LocalDateTime.now());
        } else {
            country.setCreated(dto.getCreated());
        }
        
        country.setUpdated(LocalDateTime.now());
        
        return country;
    }
    
    public CountryDto toDto(Country entity) {
        if (entity == null) {
            return null;
        }
        
        CountryDto dto = new CountryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAlpha2(entity.getAlpha2());
        dto.setAlpha3(entity.getAlpha3());
        dto.setStatus(entity.getStatus());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        
        return dto;
    }
} 