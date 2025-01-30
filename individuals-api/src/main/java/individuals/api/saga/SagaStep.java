package individuals.api.saga;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SagaStep {
    private String name;
    private Runnable action;
    private Runnable compensatingAction;
    private boolean completed;
}