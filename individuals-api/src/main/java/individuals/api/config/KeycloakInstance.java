package individuals.api.config;



import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class KeycloakInstance {
    private final String url;
    private final String realm;
    private final String clientId;

    public KeycloakInstance(String url, String realm, String clientId) {
        this.url = url;

        this.realm = realm;
        this.clientId = clientId;
    }
}