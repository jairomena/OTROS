create table if not exists public.producto
(
    id integer generated by default as identity
        primary key,
    nombre varchar(50),
    precio  numeric(6, 2)
);