ALTER TABLE person.users
    ADD CONSTRAINT chk_email_format
        CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$');

ALTER TABLE person.individuals
    ADD CONSTRAINT chk_passport_length
        CHECK (length(passport_number) BETWEEN 6 AND 20);

ALTER TABLE person.individuals
    ADD CONSTRAINT chk_phone_format
        CHECK (phone_number ~* '^\+?[0-9]{10,14}$');