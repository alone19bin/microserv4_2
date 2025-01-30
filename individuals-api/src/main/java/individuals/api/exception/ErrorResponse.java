package individuals.api.exception;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String errorCode;
    private String path;

    public ErrorResponse(String message) {
        this.message = message;
    }
}