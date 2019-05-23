create table APP.OPERATIONS
(
    ID            INTEGER generated always as identity
        constraint OPERATIONS_PK
            primary key,
    DATE          TIMESTAMP                  not null,
    CURRENCY_BUY  VARCHAR(4)                 not null,
    CURRENCY_SALE VARCHAR(4),
    RATE          DOUBLE                     not null,
    BUY_SUMM      DOUBLE                     not null,
    SALE_SUMM     DOUBLE                     not null,
    STATUS        VARCHAR(10) default 'LIVE' not null
)

