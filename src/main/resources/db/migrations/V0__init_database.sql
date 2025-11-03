create schema if not exists "dnd-editor";

create table if not exists "dnd-editor"."users" (
    id UUID not null unique primary key,
    username varchar(50) not null,
    email varchar(100) not null,
    password varchar(255) not null,
    created_at timestamp not null,
    updated_at timestamp
)