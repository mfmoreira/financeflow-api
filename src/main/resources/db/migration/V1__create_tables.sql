CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL
);

CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    name VARCHAR(100),
    balance DECIMAL(15,2) DEFAULT 0
);

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(id),
    type VARCHAR(20),
    category VARCHAR(100),
    amount DECIMAL(15,2),
    created_at TIMESTAMP DEFAULT NOW()
);

