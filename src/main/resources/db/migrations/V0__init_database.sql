    create schema if not exists dnd_editor;

create table if not exists dnd_editor.users (
    id UUID not null unique primary key,
    username varchar(50) not null,
    email varchar(100) not null,
    password varchar(255) not null,
    created_at timestamp not null,
    updated_at timestamp
);

CREATE TABLE if not exists dnd_editor.token (
    id UUID not null unique primary key,
    access_token TEXT,
    refresh_token TEXT,
    is_logged_out BOOLEAN DEFAULT FALSE,
    user_id UUID not null,
    constraint fk_user
        foreign key (user_id) references dnd_editor.users(id) on delete cascade
);
