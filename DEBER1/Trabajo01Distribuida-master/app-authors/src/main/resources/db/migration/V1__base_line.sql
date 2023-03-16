
create table if not exists authors
(
    id         serial
    primary key,
    first_name varchar(100),
    last_name  varchar(100)
);

alter table authors
    owner to postgres;

create table if not exists books
(
    id        serial
    primary key,
    author_id integer
    references authors
    on update cascade on delete cascade,
    isbn      varchar(64),
    title     varchar(100),
    price     numeric(10, 2)
    );

alter table books
    owner to postgres;
