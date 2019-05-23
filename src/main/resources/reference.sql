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
)

