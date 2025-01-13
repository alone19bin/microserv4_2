package individuals.common.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CountryDto {
    private Integer id;
    private String name;
    private String alpha2;
    private String alpha3;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String status;
}