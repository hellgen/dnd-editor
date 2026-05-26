package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;

import java.util.UUID;

public class CharacterMapper {

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

        return userCharacter;
    }
}
