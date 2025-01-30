CREATE TABLE person.countries (
                                  id SERIAL PRIMARY KEY,
                                  created TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                  updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                  name VARCHAR(64) NOT NULL UNIQUE,
                                  alpha2 VARCHAR(2) NOT NULL UNIQUE,
                                  alpha3 VARCHAR(3) NOT NULL UNIQUE,
                                  status person.status_type NOT NULL
);