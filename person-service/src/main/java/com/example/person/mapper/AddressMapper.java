package com.example.person.mapper;

import com.example.dto.AddressDto;
import com.example.person.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AddressMapper {
    private final CountryMapper countryMapper;
    
    public Address toEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        
        Address address = new Address();
        address.setId(dto.getId());
        address.setAddress(dto.getAddress());
        address.setZipCode(dto.getZipCode());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(countryMapper.toEntity(dto.getCountry()));
        
        if (dto.getCreated() == null) {
            address.setCreated(LocalDateTime.now());
        } else {
            address.setCreated(dto.getCreated());
        }
        
        address.setUpdated(LocalDateTime.now());
        address.setArchived(LocalDateTime.now().plusYears(100));
        
        return address;
    }
    
    public AddressDto toDto(Address entity) {
        if (entity == null) {
            return null;
        }
        
        AddressDto dto = new AddressDto();
        dto.setId(entity.getId());
        dto.setAddress(entity.getAddress());
        dto.setZipCode(entity.getZipCode());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        dto.setArchived(entity.getArchived());
        dto.setCountry(countryMapper.toDto(entity.getCountry()));
        
        return dto;
    }
} 