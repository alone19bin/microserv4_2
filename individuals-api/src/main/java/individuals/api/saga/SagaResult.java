package individuals.api.saga;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SagaResult<R> {
    private SagaStatus status;
    private R result;
    private String errorMessage;
}