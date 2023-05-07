CREATE TABLE webhook (
    id              INTEGER PRIMARY KEY,
    user_id         INTEGER,
    url      VARCHAR(200),
    payment_method  VARCHAR(100),
    payment_state   VARCHAR(20)
);