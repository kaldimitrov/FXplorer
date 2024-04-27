CREATE TABLE exchange_rates (
    id SERIAL PRIMARY KEY,
    source_currency_id INT NOT NULL,
    target_currency_id INT NOT NULL,
    rate NUMERIC(10, 6) NOT NULL,
    date DATE NOT NULL,
    CONSTRAINT fk_source_currency FOREIGN KEY (source_currency_id) REFERENCES currencies(id),
    CONSTRAINT fk_target_currency FOREIGN KEY (target_currency_id) REFERENCES currencies(id),
    CONSTRAINT uc_currency_date UNIQUE (source_currency_id, target_currency_id, date)
);
