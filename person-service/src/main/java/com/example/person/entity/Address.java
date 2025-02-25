package com.example.person.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "addresses", schema = "person")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private LocalDateTime created;
    private LocalDateTime updated;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;
    
    private String address;
    
    @Column(name = "zip_code")
    private String zipCode;
    
    private LocalDateTime archived;
    private String city;
    private String state;
} 