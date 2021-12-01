CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE category (
    id uuid DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE account (
    id uuid DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO account (name, email, username, password)
    VALUES ('admin', 'admin@gmail.com', 'admin', '$2a$12$5OK3yU8vciIk5ZhwI1cbx.m8THilxFM3ggLaLYGHtY1K9Dl122yfu');

CREATE TABLE entry (
    id uuid DEFAULT uuid_generate_v4(),
    description VARCHAR(100) NOT NULL,
    date DATE,
    value DECIMAL NOT NULL,
    type VARCHAR(7) NOT NULL,
    category_id uuid NOT NULL,
    account_id uuid NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id),
    FOREIGN KEY (account_id) REFERENCES account (id)
);