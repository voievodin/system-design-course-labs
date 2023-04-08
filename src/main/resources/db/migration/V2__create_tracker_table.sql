CREATE TABLE tracker (
        id                 INTEGER PRIMARY KEY,
        method             VARCHAR(100),
        currency           VARCHAR(3),
        tracker_id         INTEGER,
        amount_limit       DECIMAL,
        amount_limit_type  VARCHAR (10)
);