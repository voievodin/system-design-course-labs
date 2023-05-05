CREATE DATABASE `donations`;
create table if not exists account
(
    id        int auto_increment
        primary key,
    balance   decimal default 0 null,
    is_opened smallint          null,
    currency  varchar(3)        null
);

create table if not exists payment
(
    id         int auto_increment
        primary key,
    amount     decimal      null,
    method     varchar(100) null,
    currency   varchar(3)   null,
    user_id    int          null,
    state      varchar(20)  null,
    created_at timestamp    null
);

INSERT INTO account (balance, is_opened, currency) VALUES
    (0, 1, 'EUR');
INSERT INTO account (balance, is_opened, currency) VALUES
    (0, 1, 'USD');
INSERT INTO account (balance, is_opened, currency) VALUES
    (0, 1, 'UAH');

