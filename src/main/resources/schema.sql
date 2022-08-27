create table item
(
    sku   varchar          not null
        constraint item_pk
            primary key,
    name  varchar          not null,
    price double precision not null
);
create unique index item_sku_uindex
    on item (sku);

create table "order"
(
    id    varchar(12) not null
        constraint order_pk
            primary key,
    email varchar(32) not null
);

create table order_item
(
    id       serial
        constraint order_item_pk
            primary key,
    qty      integer not null,
    order_id varchar(12)
        constraint order_item_order_id_fk
            references "order"
            on update cascade on delete cascade,
    item_id  varchar
        constraint order_item_item_sku_fk
            references item
            on update cascade on delete cascade
);

create table return
(
    id       serial
        constraint return_pk
            primary key,
    order_id varchar(12) not null
        constraint return_order_id_fk
            references "order"
            on update cascade on delete cascade
);

create table return_item
(
    id        serial
        constraint return_item_pk
            primary key,
    return_id integer
        constraint return_item_return_id_fk
            references return
            on update cascade on delete cascade,
    qty       integer,
    item_id   varchar
        constraint return_item_item_sku_fk
            references item
            on update cascade on delete cascade,
    status    varchar
);

create table return_token
(
    id       serial
        constraint return_token_pk
            primary key,
    token    varchar(32),
    order_id varchar(12)
        constraint return_token_order_id_fk
            references "order"
            on update cascade on delete cascade
);
create unique index return_token_token_uindex
    on return_token (token);