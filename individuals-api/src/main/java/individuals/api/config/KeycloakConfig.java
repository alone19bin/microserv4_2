package individuals.api.config;

import individuals.api.config.KeycloakInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Bean
    public KeycloakInstance keycloakInstance() {
        return new KeycloakInstance(keycloakUrl, realm, clientId);
    }
}