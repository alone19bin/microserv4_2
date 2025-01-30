CREATE TABLE person.user_history (
                                     id UUID PRIMARY KEY DEFAULT public.uuid_generate_v4(),
                                     created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     user_id UUID NOT NULL REFERENCES person.users(id),
                                     user_type VARCHAR(32) NOT NULL,
                                     reason VARCHAR(255) NOT NULL,
                                     comment TEXT,
                                     changed_values JSONB NOT NULL
);

CREATE INDEX idx_history_user ON person.user_history(user_id);