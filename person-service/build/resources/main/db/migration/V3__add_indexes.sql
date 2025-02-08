-- Создание индексов для оптимизации запросов
CREATE INDEX idx_users_email ON person.users(email);
CREATE INDEX idx_individuals_phone ON person.individuals(phone_number);
CREATE INDEX idx_individuals_status ON person.individuals(status);