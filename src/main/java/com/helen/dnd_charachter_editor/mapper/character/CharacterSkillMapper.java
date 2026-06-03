package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.entity.character.CharacterSkill;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Skill;

/**
 * Маппер `CharacterSkillMapper` для преобразования данных между слоями приложения.
 */
public class CharacterSkillMapper {

    /**
     * Преобразует данные для запрошенной операции.
     * @param character параметр, используемый при выполнении операции
     * @param skill параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static CharacterSkill toEntity(UserCharacter character, Skill skill) {
        CharacterSkill characterSkill = new CharacterSkill();
        characterSkill.setCharacter(character);
        characterSkill.setSkill(skill);
        characterSkill.setProficiencyLevel(0);

        return characterSkill;
    }
}
