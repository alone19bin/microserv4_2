CREATE TABLE person.individuals (
                                    id UUID PRIMARY KEY DEFAULT public.uuid_generate_v4(),
                                    user_id UUID NOT NULL UNIQUE REFERENCES person.users(id), -- users должен быть создан ранее
                                    passport_number VARCHAR(32) NOT NULL UNIQUE,
                                    phone_number VARCHAR(20) NOT NULL UNIQUE CHECK (phone_number ~* '^\+?[1-9]\d{1,14}$'),
                                    email VARCHAR(320) CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
                                    verified_at TIMESTAMP WITH TIME ZONE,
                                    archived_at TIMESTAMP WITH TIME ZONE,
                                    status person.status_type NOT NULL
);