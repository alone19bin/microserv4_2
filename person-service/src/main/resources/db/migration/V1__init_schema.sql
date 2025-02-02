CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;
CREATE SCHEMA IF NOT EXISTS person;

CREATE TYPE person.status_type AS ENUM (
    'ACTIVE',
    'INACTIVE',
    'PENDING',
    'ARCHIVED'
    );