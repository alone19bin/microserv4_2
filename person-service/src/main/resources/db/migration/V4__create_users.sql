CREATE INDEX idx_users_email ON person.users(email);
CREATE INDEX idx_individuals_phone ON person.individuals(phone_number);
CREATE INDEX idx_addresses_city ON person.addresses(city);

CREATE OR REPLACE FUNCTION update_modified_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_users_modtime
    BEFORE UPDATE ON person.users
    FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_individuals_modtime
    BEFORE UPDATE ON person.individuals
    FOR EACH ROW
EXECUTE FUNCTION update_modified_column();

CREATE TRIGGER update_addresses_modtime
    BEFORE UPDATE ON person.addresses
    FOR EACH ROW
EXECUTE FUNCTION update_modified_column();