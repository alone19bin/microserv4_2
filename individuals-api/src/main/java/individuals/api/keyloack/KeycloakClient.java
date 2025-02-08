package individuals.api.keyloack;

import individuals.common.dto.UserDto;
import lombok.Value;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Component
public class KeycloakClient {
    private final Keycloak keycloak;
    private final String realm;

    public KeycloakClient(
            Keycloak keycloak,
            @org.springframework.beans.factory.annotation.Value("${keycloak.realm}") String realm
    ) {
        this.keycloak = keycloak;
        this.realm = realm;
    }

    public String createUser(String firstName, String lastName, String email, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        user.setCredentials(Collections.singletonList(credential));

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);
        return response.getLocation().getPath().replaceAll(".*/", "");
    }

    public void deleteUser(String userId) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        usersResource.delete(userId);
    }
}