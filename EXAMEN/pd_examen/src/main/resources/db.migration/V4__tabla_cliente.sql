create table if not exists public.cliente
(
    id integer generated by default as identity
        primary key,
    nombre   varchar(16) not null,
    precio   numeric
);