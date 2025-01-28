CREATE TABLE IF NOT EXISTS person.sagas (
                                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                            user_id UUID NOT NULL,
                                            individual_id UUID NOT NULL,
                                            keycloak_user_id VARCHAR(255) NOT NULL,
                                            status VARCHAR(50) NOT NULL,
                                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);