package com.example.person.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "user_history", schema = "person")
@Getter
@Setter
public class UserHistory {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String userType;
    private String reason;
    private String comment;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> changedValues;
} 