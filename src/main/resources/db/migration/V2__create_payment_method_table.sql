CREATE TABLE payment_method (
    id                              INTEGER PRIMARY KEY,
    user_id                         INTEGER,
    name                            VARCHAR(100),
    is_available                    BOOLEAN,
    availability_timestamp_from     TIMESTAMP
);