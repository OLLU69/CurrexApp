create table APP.REFERENCE
(
    ID        INTEGER generated always as identity
        constraint TABLE_NAME_PK primary key,
    NUM_CODE  INTEGER      not null
        constraint TABLE_NAME_NUM_CODE_UINDEX
            unique,
    STR_CODE  VARCHAR(4)   not null
        constraint TABLE_NAME_STR_CODE_UINDEX
            unique,
    NAME      VARCHAR(256) not null,
    RATE      DOUBLE       not null,
    SALE_RATE DOUBLE       not null,
    BUY_RATE  DOUBLE       not null
);

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
);

