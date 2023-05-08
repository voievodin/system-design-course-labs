CREATE TABLE payment (
    id          INTEGER PRIMARY KEY,
    amount      DECIMAL,
    method      VARCHAR(100),
    currency    VARCHAR(3),
    user_id     INTEGER,
    state       VARCHAR(20),
    created_at  TIMESTAMP
);
