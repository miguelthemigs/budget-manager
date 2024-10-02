-- Enum for Currency
CREATE TYPE currency AS ENUM (
    'USD', 'EUR', 'BRL', 'JPY', 'GBP', 'AUD', 'CAD', 'CHF', 'SEK', 'NOK', 'MXN'
);

-- Enum for Role
CREATE TYPE role AS ENUM ('USER', 'ADMIN');

-- Enum for Category
CREATE TYPE category AS ENUM (
    'GROCERIES', 'SHOPPING', 'ENTERTAINMENT', 'HEALTH', 'TRANSFERS', 'RESTAURANTS', 'TRAVEL', 'TRANSPORTATION',
    'UTILITIES', 'SERVICES', 'INVESTMENTS', 'DONATION', 'SALARY', 'GIFTS', 'INSURANCE', 'SUBSCRIPTIONS', 'REFUND', 'OTHER'
);

-- Create User table
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,               -- Auto-generated sequential IDs
                       name VARCHAR(255) NOT NULL,             -- User's name
                       email VARCHAR(255) NOT NULL UNIQUE,     -- Email should be unique
                       password VARCHAR(255),                  -- Password (stored securely, consider encrypting)
                       balance DOUBLE PRECISION DEFAULT 0.0,   -- Default balance is 0.0
                       preferred_currency currency,            -- Preferred currency
                       monthly_budget DOUBLE PRECISION,        -- Monthly budget
                       category_budgets JSONB,                 -- Category budgets (stored as JSONB)
                       role role NOT NULL DEFAULT 'USER'       -- Role (default is 'USER')
);

-- Ensure email uniqueness
CREATE UNIQUE INDEX idx_users_email ON users(email);
