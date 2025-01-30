package individuals.api.saga;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SagaContext {
    private UUID transactionId;
    private UUID userId;
    private List<String> completedSteps = new ArrayList<>();

    public void addCompletedStep(String stepName) {
        completedSteps.add(stepName);
    }
}