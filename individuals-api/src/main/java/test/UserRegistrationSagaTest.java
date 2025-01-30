package test;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
@Testcontainers
public class UserRegistrationSagaTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    private UserRegistrationSagaOrchestrator sagaOrchestrator;

    @Test
    void testSuccessfulUserRegistration() {
        UserRegistrationDTO dto = createTestUserDTO();

        UserRegistrationSaga result = sagaOrchestrator.orchestrateRegistration(dto);

        assertThat(result.getStatus()).isEqualTo(SagaStatus.COMPLETED);
        assertThat(result.getUserId()).isNotNull();
    }

    @Test
    void testFailedKeycloakRegistration() {
        UserRegistrationDTO dto = createTestUserDTO();

        // Имитация падения Keycloak
        UserRegistrationSaga result = sagaOrchestrator.orchestrateRegistration(dto);

        assertThat(result.getStatus()).isEqualTo(SagaStatus.COMPENSATED);
        assertThat(result.getUserId()).isNull();
    }
}