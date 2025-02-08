package individuals.api.saga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationSagaResult {
    private SagaStatus status;
    private UserRegistrationResponse response;
    private String errorMessage;
}