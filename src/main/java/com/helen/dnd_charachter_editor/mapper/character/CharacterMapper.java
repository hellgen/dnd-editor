package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;

/**
 * Маппер `CharacterMapper` для преобразования данных между слоями приложения.
 */
public class CharacterMapper {

    /**
     * Преобразует данные для запрошенной операции.
     * @param createCharacterRequest параметр, используемый при выполнении операции
     * @param user параметр, используемый при выполнении операции
     * @param race параметр, используемый при выполнении операции
     * @param subrace параметр, используемый при выполнении операции
     * @param characterClass параметр, используемый при выполнении операции
     * @param classArchetype параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    public static UserCharacter mapToUserCharacter(
            CreateCharacterRequest createCharacterRequest,
            User user,
            Race race,
            Subrace subrace,
            CharacterClass characterClass,
            ClassArchetype classArchetype
    ) {
        UserCharacter userCharacter = new UserCharacter();

        userCharacter.setUser(user);
        userCharacter.setName(createCharacterRequest.characterName());
        userCharacter.setRace(race);
        userCharacter.setSubrace(subrace);
        userCharacter.setClassField(characterClass);
        userCharacter.setClassArchetype(classArchetype);
        userCharacter.setLevel(createCharacterRequest.level());
        userCharacter.setMaxHealth(createCharacterRequest.maxHealth());
        userCharacter.setCurrentHealth(createCharacterRequest.currentHealth());
        userCharacter.setAppearance(createCharacterRequest.appearance());
        userCharacter.setArmorClass(createCharacterRequest.armorClass());
        userCharacter.setInventory(CharacterResponseMapper.serializeInventory(createCharacterRequest.inventory()));
        userCharacter.setAbilities(CharacterResponseMapper.serializeIds(createCharacterRequest.abilities()));
        userCharacter.setSpells(CharacterResponseMapper.serializeIds(createCharacterRequest.spells()));
        userCharacter.setSavingThrowsCount(Math.min(createCharacterRequest.savingThrowsCount(), 2));

        userCharacter.setPlatinum(createCharacterRequest.platinum());
        userCharacter.setGold(createCharacterRequest.gold());
        userCharacter.setElectrum(createCharacterRequest.electrum());
        userCharacter.setSilver(createCharacterRequest.silver());
        userCharacter.setCopper(createCharacterRequest.copper());

        return userCharacter;
    }
}
