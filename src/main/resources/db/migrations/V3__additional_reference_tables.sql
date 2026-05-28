create table if not exists dnd_editor.abilities
(
    ability_id UUID         not null primary key,
    code       varchar(20)  not null,
    name       varchar(100) not null
);

CREATE TABLE IF NOT EXISTS dnd_editor.class_features
(
    class_feature_id    UUID PRIMARY KEY,
    class_id            UUID         NOT NULL,
    feature_name        VARCHAR(100) NOT NULL,
    feature_description VARCHAR(1024),
    level_required      INT          NOT NULL,

    CONSTRAINT fk_class_features_class
        FOREIGN KEY (class_id)
            REFERENCES dnd_editor.classes (class_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dnd_editor.class_archetype_features
(
    class_archetype_feature_id UUID PRIMARY KEY,
    class_archetype_id         UUID         NOT NULL,
    feature_name               VARCHAR(100) NOT NULL,
    feature_description        VARCHAR(1024),
    level_required             INT          NOT NULL,

    CONSTRAINT fk_class_archetype_features_archetype
        FOREIGN KEY (class_archetype_id)
            REFERENCES dnd_editor.class_archetypes (class_archetype_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dnd_editor.race_features
(
    race_feature_id     UUID PRIMARY KEY,
    race_id             UUID         NOT NULL,
    feature_name        VARCHAR(100) NOT NULL,
    feature_description VARCHAR(1024),

    CONSTRAINT fk_race_features_race
        FOREIGN KEY (race_id)
            REFERENCES dnd_editor.races (race_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dnd_editor.subrace_features
(
    subrace_feature_id  UUID PRIMARY KEY,
    subrace_id          UUID         NOT NULL,
    feature_name        VARCHAR(100) NOT NULL,
    feature_description VARCHAR(1024),

    CONSTRAINT fk_subrace_features_subrace
        FOREIGN KEY (subrace_id)
            REFERENCES dnd_editor.subraces (subrace_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dnd_editor.race_ability_bonuses
(
    race_ability_bonus_id UUID PRIMARY KEY,
    race_id               UUID NOT NULL,
    ability_id            UUID NOT NULL,
    bonus_value           INT  NOT NULL,

    CONSTRAINT fk_race_ability_bonuses_race
        FOREIGN KEY (race_id)
            REFERENCES dnd_editor.races (race_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_race_ability_bonuses_ability
        FOREIGN KEY (ability_id)
            REFERENCES dnd_editor.abilities (ability_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dnd_editor.subrace_ability_bonuses
(
    subrace_ability_bonus_id UUID PRIMARY KEY,
    subrace_id               UUID NOT NULL,
    ability_id               UUID NOT NULL,
    bonus_value              INT  NOT NULL,

    CONSTRAINT fk_subrace_ability_bonuses_subrace
        FOREIGN KEY (subrace_id)
            REFERENCES dnd_editor.subraces (subrace_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_subrace_ability_bonuses_ability
        FOREIGN KEY (ability_id)
            REFERENCES dnd_editor.abilities (ability_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dnd_editor.spells
(
    spell_id          UUID PRIMARY KEY,
    spell_name        VARCHAR(100) NOT NULL,
    spell_level       INT          NOT NULL,
    spell_school      VARCHAR(100) NOT NULL,
    casting_time      VARCHAR(100) NOT NULL,
    spell_range       VARCHAR(100) NOT NULL,
    components        VARCHAR(100) NOT NULL,
    duration          VARCHAR(100) NOT NULL,
    spell_description VARCHAR(2048)
);