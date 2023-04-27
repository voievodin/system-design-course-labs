CREATE TABLE webhook (
    id              INTEGER PRIMARY KEY,
    target_url      VARCHAR(250),
    payment_method  VARCHAR(100),
    payment_state   VARCHAR(20),
    user_id         INTEGER
);
