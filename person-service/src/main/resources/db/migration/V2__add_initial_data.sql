
INSERT INTO person.countries (id, name, code) VALUES
                                                  (public.uuid_generate_v4(), 'Российская Федерация', 'RU'),
                                                  (public.uuid_generate_v4(), 'Республика Беларусь', 'BY'),
                                                  (public.uuid_generate_v4(), 'Республика Казахстан', 'KZ');


CREATE TYPE person.individual_status AS ENUM ('CREATED', 'VERIFIED', 'BLOCKED', 'ARCHIVED');

CREATE OR REPLACE FUNCTION person.update_modified_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_users_modtime
    BEFORE UPDATE ON person.users
    FOR EACH ROW
EXECUTE FUNCTION person.update_modified_column();

CREATE TRIGGER update_addresses_modtime
    BEFORE UPDATE ON person.addresses
    FOR EACH ROW
EXECUTE FUNCTION person.update_modified_column();