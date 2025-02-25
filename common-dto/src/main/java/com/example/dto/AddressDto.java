package com.example.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AddressDto {
    private UUID id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private CountryDto country;
    private String address;
    private String zipCode;
    private LocalDateTime archived;
    private String city;
    private String state;
}