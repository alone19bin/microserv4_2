


DROP SCHEMA IF EXISTS person CASCADE;
CREATE SCHEMA person;

-- Создание расширения с явным указанием схемы
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA public;

-- Установка пути поиска
SET search_path TO person, public;

-- Создание функции UUID в схеме person
CREATE OR REPLACE FUNCTION person.uuid_generate_v4()
    RETURNS UUID AS $$
SELECT public.uuid_generate_v4();
$$ LANGUAGE SQL;

-- Countries table
CREATE TABLE person.countries (
                                  id UUID PRIMARY KEY DEFAULT person.uuid_generate_v4(),
                                  name VARCHAR(255) NOT NULL,
                                  code VARCHAR(255) NOT NULL UNIQUE
);

-- Addresses table
CREATE TABLE person.addresses (
                                  id UUID PRIMARY KEY DEFAULT person.uuid_generate_v4(),
                                  created TIMESTAMP NOT NULL,
                                  updated TIMESTAMP NOT NULL,
                                  country_id UUID,
                                  address VARCHAR(128),
                                  zip_code VARCHAR(32),
                                  archived TIMESTAMP NOT NULL,
                                  city VARCHAR(32),
                                  state VARCHAR(32),
                                  FOREIGN KEY (country_id) REFERENCES person.countries(id)
);

-- Users table
CREATE TABLE person.users (
                              id UUID PRIMARY KEY DEFAULT person.uuid_generate_v4(),
                              secret_key VARCHAR(32),
                              email VARCHAR(1024),
                              created TIMESTAMP NOT NULL,
                              updated TIMESTAMP NOT NULL,
                              first_name VARCHAR(32),
                              last_name VARCHAR(32),
                              filled BOOLEAN DEFAULT FALSE,
                              role VARCHAR(32) DEFAULT 'USER',
                              address_id UUID,
                              FOREIGN KEY (address_id) REFERENCES person.addresses(id)
);

-- Individuals table
CREATE TABLE person.individuals (
                                    id UUID PRIMARY KEY DEFAULT person.uuid_generate_v4(),
                                    user_id UUID NOT NULL UNIQUE,
                                    passport_number VARCHAR(32),
                                    phone_number VARCHAR(32),
                                    email VARCHAR(1024),
                                    verified_at TIMESTAMP,
                                    archived_at TIMESTAMP,
                                    status VARCHAR(32),
                                    FOREIGN KEY (user_id) REFERENCES person.users(id)
);