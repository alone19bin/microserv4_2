package com.example.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class IndividualDto {
    private UUID id;
    private UserDto user;
    private String passportNumber;
    private String phoneNumber;
    private String email;
    private LocalDateTime verifiedAt;
    private LocalDateTime archivedAt;
    private String status;
}