CREATE TABLE if not exists dnd_editor.races
(
    race_id          UUID PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(512),
    age         INT          NOT NULL,
    height      INT          NOT NULL,
    speed       INT          NOT NULL,
    languages   VARCHAR(512)
);

CREATE TABLE if not exists dnd_editor.subraces
(
    subrace_id          UUID PRIMARY KEY,
    race_id     UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(512),

    CONSTRAINT fk_subrace_race
        FOREIGN KEY (race_id)
            REFERENCES dnd_editor.races(race_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_subrace UNIQUE (race_id, name)
);

drop table dnd_editor.classes cascade;

CREATE TABLE IF NOT EXISTS dnd_editor.classes
(
    class_id                  UUID PRIMARY KEY,
    class_name                VARCHAR(100) NOT NULL,
    class_description         VARCHAR(512),

    is_spellcaster            BOOLEAN      NOT NULL DEFAULT false,
    spellcasting_start_level  INT,

    CONSTRAINT uq_classes_class_name
        UNIQUE (class_name),

    CONSTRAINT chk_classes_spellcasting_start_level
        CHECK (
            (
                is_spellcaster = false
                    AND spellcasting_start_level IS NULL
                )
                OR
            (
                is_spellcaster = true
                    AND spellcasting_start_level IS NOT NULL
                    AND spellcasting_start_level > 0
                )
            )
);

CREATE TABLE if not exists dnd_editor.skills
(
    skill_id      UUID PRIMARY KEY,
    name    VARCHAR(100) NOT NULL UNIQUE,
    ability VARCHAR(10)  NOT NULL
);