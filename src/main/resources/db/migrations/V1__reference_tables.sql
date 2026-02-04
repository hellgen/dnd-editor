CREATE TABLE if not exists dnd_editor.race
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(512),
    age         INT          NOT NULL,
    height      INT          NOT NULL,
    speed       INT          NOT NULL,
    languages   VARCHAR(512)
);

CREATE TABLE if not exists dnd_editor.subrace
(
    id          UUID PRIMARY KEY,
    race_id     UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(512),

    CONSTRAINT fk_subrace_race
        FOREIGN KEY (race_id)
            REFERENCES dnd_editor.race (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_subrace UNIQUE (race_id, name)
);

CREATE TABLE if not exists dnd_editor.class
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(100) NOT NULL UNIQUE,
    hit_die         INT          NOT NULL,
    primary_ability VARCHAR(10)  NOT NULL
);

CREATE TABLE if not exists dnd_editor.class_archetype
(
    id          UUID PRIMARY KEY,
    class_id    UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(512),

    CONSTRAINT fk_archetype_class
        FOREIGN KEY (class_id)
            REFERENCES dnd_editor.class (id)
            ON DELETE CASCADE,

    CONSTRAINT uq_class_archetype UNIQUE (class_id, name)
);

CREATE TABLE if not exists dnd_editor.skill
(
    id      UUID PRIMARY KEY,
    name    VARCHAR(100) NOT NULL UNIQUE,
    ability VARCHAR(10)  NOT NULL
);