-- Create User Table
DELETE TABLE IF EXISTS user_entity;
CREATE TABLE IF NOT EXISTS user_entity (
                                           id BIGSERIAL PRIMARY KEY,                -- Auto-generated sequential IDs
                                           name VARCHAR(255) NOT NULL,              -- User's name
    email VARCHAR(255) NOT NULL UNIQUE,      -- Email should be unique
    password VARCHAR(255) NOT NULL,          -- Password (stored securely, consider encrypting)
    balance DOUBLE PRECISION DEFAULT 0.0,    -- Default balance is 0.0
    preferred_currency currency,              -- Preferred currency (ENUM type)
    monthly_budget DOUBLE PRECISION,          -- Monthly budget
    role role NOT NULL DEFAULT 'USER'         -- Role (default is 'USER')
    );

-- Ensure email uniqueness
CREATE UNIQUE INDEX IF NOT EXISTS idx_users_email ON user_entity(email);
