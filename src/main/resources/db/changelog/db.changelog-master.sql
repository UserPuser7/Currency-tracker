-- liquibase formatted sql
-- changeset author:1
CREATE TABLE currencies (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    base_currency VARCHAR(3) DEFAULT 'RUB' NOT NULL,
    price_change_range VARCHAR(50) NOT NULL,
    description TEXT
);