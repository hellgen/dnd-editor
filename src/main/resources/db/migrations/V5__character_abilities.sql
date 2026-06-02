CREATE TABLE IF NOT EXISTS dnd_editor.character_abilities
(
    character_ability_id UUID PRIMARY KEY,

    character_id UUID NOT NULL,
    ability_id   UUID NOT NULL,

    value INT NOT NULL,

    CONSTRAINT fk_character_abilities_character
        FOREIGN KEY (character_id)
            REFERENCES dnd_editor.characters(character_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_character_abilities_ability
        FOREIGN KEY (ability_id)
            REFERENCES dnd_editor.abilities(ability_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_character_abilities_character_ability
        UNIQUE (character_id, ability_id)
);