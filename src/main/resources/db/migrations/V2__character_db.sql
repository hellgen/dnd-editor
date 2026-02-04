CREATE TABLE IF NOT EXISTS dnd_editor.character
(
    id                 UUID PRIMARY KEY,

    user_id            UUID        NOT NULL,

    name               VARCHAR(50) NOT NULL,

    race_id            UUID        NOT NULL,
    subrace_id         UUID,
    class_id           UUID        NOT NULL,
    class_archetype_id UUID,

    level              INT         NOT NULL DEFAULT 1 CHECK (level > 0),
    max_health         INT         NOT NULL,
    current_health     INT         NOT NULL,

    appearance         VARCHAR(512),
    armor_class        INT         NOT NULL DEFAULT 10 CHECK (level > 0),
    inventory          VARCHAR(512),
    spells             VARCHAR(512),
    created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_character_user
        FOREIGN KEY (user_id)
            REFERENCES dnd_editor.users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_character_race
        FOREIGN KEY (race_id)
            REFERENCES dnd_editor.race (id),

    CONSTRAINT fk_character_subrace
        FOREIGN KEY (subrace_id)
            REFERENCES dnd_editor.subrace (id),

    CONSTRAINT fk_character_class
        FOREIGN KEY (class_id)
            REFERENCES dnd_editor.class (id),

    CONSTRAINT fk_character_archetype
        FOREIGN KEY (class_archetype_id)
            REFERENCES dnd_editor.class_archetype (id)
);

CREATE TABLE IF NOT EXISTS dnd_editor.character_skill
(
    character_id      UUID NOT NULL,
    skill_id          UUID NOT NULL,
    proficiency_level INT  NOT NULL DEFAULT 0,

    PRIMARY KEY (character_id, skill_id),

    CONSTRAINT fk_cs_character
        FOREIGN KEY (character_id)
            REFERENCES dnd_editor.character (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_cs_skill
        FOREIGN KEY (skill_id)
            REFERENCES dnd_editor.skill (id)
);

CREATE TABLE character_wallet
(
    character_id UUID PRIMARY KEY,
    gold         INT DEFAULT 0,
    silver       INT DEFAULT 0,
    copper       INT DEFAULT 0,
    platinum     INT DEFAULT 0,
    FOREIGN KEY (character_id) REFERENCES dnd_editor.character (id) ON DELETE CASCADE
);