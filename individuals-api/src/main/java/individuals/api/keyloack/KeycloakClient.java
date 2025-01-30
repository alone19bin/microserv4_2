package individuals.api.keyloack;

import lombok.Value;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class KeycloakClient {

    private final Keycloak keycloak;
    private final String realm;

    public KeycloakClient(Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.realm = realm;
    }

    public String createUser(String firstName, String lastName, String email, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setEnabled(true);

        // оздаемпользователя
       /* RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();*/
        String userId = usersResource.create(user).getLocation().getPath().replaceAll(".*/", "");

                // Устанавливаем пароль
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        usersResource.get(userId).resetPassword(credential);

        return userId;
    }

    public void deleteUser(String userId) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        usersResource.delete(userId);
    }
}