package individuals.api.saga;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SagaContext {

    private final UUID transactionId;
    private final List<String> completedSteps;
    private UUID userId;

    public SagaContext(UUID transactionId) {
        this.transactionId = transactionId;
        this.completedSteps = new ArrayList<>();
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public List<String> getCompletedSteps() {
        return completedSteps;
    }

    public void addCompletedStep(String step) {
        completedSteps.add(step);
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}