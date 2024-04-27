CREATE TABLE exchange_history (
    id UUID PRIMARY KEY,
    exchange_rate_id INT NOT NULL,
    amount NUMERIC(16, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_exchange_rate FOREIGN KEY (exchange_rate_id) REFERENCES exchange_rates(id)
);
