package model;

import lombok.Data;
import java.util.UUID;

@Data
public class Individual {
    private UUID id;
    private User user;
    private String passportNumber;
    private String phoneNumber;
}