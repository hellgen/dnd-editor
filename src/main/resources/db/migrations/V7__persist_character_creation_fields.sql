ALTER TABLE dnd_editor.characters
    ADD COLUMN IF NOT EXISTS abilities TEXT,
    ALTER COLUMN spells TYPE TEXT,
    ADD COLUMN IF NOT EXISTS saving_throws_count INT NOT NULL DEFAULT 0 CHECK (saving_throws_count >= 0);

UPDATE dnd_editor.characters ch
SET abilities = ability_ids.abilities
FROM (
         SELECT character_id, json_agg(ability_id)::text AS abilities
         FROM dnd_editor.character_abilities
         GROUP BY character_id
     ) ability_ids
WHERE ch.character_id = ability_ids.character_id
  AND ch.abilities IS NULL;

UPDATE dnd_editor.characters ch
SET spells = spell_ids.spells
FROM (
         SELECT character_id, json_agg(spell_id)::text AS spells
         FROM dnd_editor.character_spells
         GROUP BY character_id
     ) spell_ids
WHERE ch.character_id = spell_ids.character_id
  AND ch.spells IS NULL;

UPDATE dnd_editor.characters ch
SET saving_throws_count = saving_throw_counts.saving_throws_count
FROM (
         SELECT character_id, COUNT(*)::int AS saving_throws_count
         FROM dnd_editor.character_saving_throws
         WHERE proficiency_level > 0
         GROUP BY character_id
     ) saving_throw_counts
WHERE ch.character_id = saving_throw_counts.character_id;
