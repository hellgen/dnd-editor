package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Skill;

/**
 * Mapper that converts character skill mapper values between layers.
 */
public class CharacterSkillMapper {

    /**
     * Converts entity.
     * @param character value used by this operation
     * @param skill value used by this operation
     * @return result of the operation
     */
    public static CharacterSkill toEntity(UserCharacter character, Skill skill) {
        CharacterSkill characterSkill = new CharacterSkill();
        characterSkill.setCharacter(character);
        characterSkill.setSkill(skill);
        characterSkill.setProficiencyLevel(0);

        return characterSkill;
    }
}
