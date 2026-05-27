CREATE TABLE IF NOT EXISTS dnd_editor.character_spells
(
    character_spell_id UUID PRIMARY KEY,

    character_id UUID NOT NULL,
    spell_id     UUID NOT NULL,

    is_prepared BOOLEAN NOT NULL DEFAULT false,

    CONSTRAINT fk_character_spells_character
        FOREIGN KEY (character_id)
            REFERENCES dnd_editor.characters(character_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_character_spells_spell
        FOREIGN KEY (spell_id)
            REFERENCES dnd_editor.spells(spell_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_character_spells_character_spell
        UNIQUE (character_id, spell_id)
);

CREATE TABLE IF NOT EXISTS dnd_editor.character_saving_throws
(
    character_saving_throw_id UUID PRIMARY KEY,

    character_id UUID NOT NULL,
    ability_id   UUID NOT NULL,

    proficiency_level INT NOT NULL,

    CONSTRAINT fk_character_saving_throws_character
        FOREIGN KEY (character_id)
            REFERENCES dnd_editor.characters(character_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_character_saving_throws_ability
        FOREIGN KEY (ability_id)
            REFERENCES dnd_editor.abilities(ability_id)
            ON DELETE CASCADE,

    CONSTRAINT uq_character_saving_throws_character_ability
        UNIQUE (character_id, ability_id)
);

CREATE TABLE IF NOT EXISTS dnd_editor.character_inventory
(
    character_inventory_id UUID PRIMARY KEY,
    character_id           UUID    NOT NULL,
    item_id                UUID    NOT NULL,
    quantity               INT     NOT NULL,
    is_equipped            BOOLEAN NOT NULL,
    custom_description     VARCHAR(512),

    CONSTRAINT fk_character_inventory_character
        FOREIGN KEY (character_id)
            REFERENCES dnd_editor.characters(character_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_character_inventory_item
        FOREIGN KEY (item_id)
            REFERENCES dnd_editor.items(item_id)
            ON DELETE CASCADE
);