drop table if exists dnd_editor.characters cascade;
CREATE TABLE IF NOT EXISTS dnd_editor.characters
(
    character_id       UUID PRIMARY KEY,

    user_id            UUID        NOT NULL,

    name               VARCHAR(50) NOT NULL,

    race_id            UUID        NOT NULL,
    subrace_id         UUID,
    class_id           UUID        NOT NULL,
    class_archetype_id UUID,

    level              INT         NOT NULL DEFAULT 1 CHECK (level > 0),

    max_health         INT         NOT NULL CHECK (max_health > 0),
    current_health     INT         NOT NULL,

    appearance         VARCHAR(512),
    armor_class        INT         NOT NULL DEFAULT 10 CHECK (armor_class > 0),

    inventory          TEXT,
    spells             VARCHAR(512),

    platinum           INT         NOT NULL DEFAULT 0 CHECK (platinum >= 0),
    gold               INT         NOT NULL DEFAULT 0 CHECK (gold >= 0),
    electrum           INT         NOT NULL DEFAULT 0 CHECK (electrum >= 0),
    silver             INT         NOT NULL DEFAULT 0 CHECK (silver >= 0),
    copper             INT         NOT NULL DEFAULT 0 CHECK (copper >= 0),

    created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT chk_character_current_health
        CHECK (
            current_health <= max_health
                AND current_health >= -(max_health / 2)
            ),

    CONSTRAINT fk_character_user
        FOREIGN KEY (user_id)
            REFERENCES dnd_editor.users (user_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_character_race
        FOREIGN KEY (race_id)
            REFERENCES dnd_editor.races (race_id),

    CONSTRAINT fk_character_subrace
        FOREIGN KEY (subrace_id)
            REFERENCES dnd_editor.subraces (subrace_id),

    CONSTRAINT fk_character_class
        FOREIGN KEY (class_id)
            REFERENCES dnd_editor.classes (class_id),

    CONSTRAINT fk_character_archetype
        FOREIGN KEY (class_archetype_id)
            REFERENCES dnd_editor.class_archetypes (class_archetype_id)
);