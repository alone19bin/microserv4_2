CREATE SCHEMA IF NOT EXISTS person;

CREATE TABLE person.saga_transactions (
                                          id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                          status VARCHAR(32) NOT NULL DEFAULT 'STARTED',
                                          user_id UUID,
                                          user_email VARCHAR(1024),
                                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          error_message TEXT
);

CREATE TABLE person.saga_completed_steps (
                                             saga_id UUID REFERENCES person.saga_transactions(id),
                                             completed_steps VARCHAR(64) NOT NULL,
                                             PRIMARY KEY (saga_id, completed_steps)
);

CREATE TABLE person.saga_compensation_actions (
                                                  saga_id UUID REFERENCES person.saga_transactions(id),
                                                  compensation_step VARCHAR(64) NOT NULL,
                                                  compensation_details TEXT,
                                                  PRIMARY KEY (saga_id, compensation_step)
);