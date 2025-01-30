package individuals.api.saga;

public interface SagaOrchestrator<T, R> {
    SagaResult<R> execute(T request);
    void compensate(SagaContext context);
}