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

CREATE TABLE IF NOT EXISTS dnd_editor.character_skills
(
    character_skill_id UUID PRIMARY KEY,

    character_id UUID NOT NULL,
    skill_id     UUID NOT NULL,

    proficiency_level INT NOT NULL,

    CONSTRAINT fk_character_skills_character
        FOREIGN KEY (character_id)
            REFERENCES dnd_editor.characters(character_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_character_skills_skill
        FOREIGN KEY (skill_id)
            REFERENCES dnd_editor.skills(skill_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_character_skills_character_skill
        UNIQUE (character_id, skill_id)
);