package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;
import com.helen.dnd_charachter_editor.entity.auth.User;
import com.helen.dnd_charachter_editor.entity.character.UserCharacter;
import com.helen.dnd_charachter_editor.entity.reference.table.Ability;
import com.helen.dnd_charachter_editor.entity.reference.table.CharacterClass;
import com.helen.dnd_charachter_editor.entity.reference.table.ClassArchetype;
import com.helen.dnd_charachter_editor.entity.reference.table.Race;
import com.helen.dnd_charachter_editor.entity.reference.table.Subrace;
import com.helen.dnd_charachter_editor.mapper.character.CharacterMapper;
import com.helen.dnd_charachter_editor.repository.character.CharacterAbilityRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterRepository;
import com.helen.dnd_charachter_editor.service.auth.AuthService;
import com.helen.dnd_charachter_editor.service.character.CharacterAbilityService;
import com.helen.dnd_charachter_editor.service.character.CharacterService;
import com.helen.dnd_charachter_editor.service.character.CharacterWalletService;
import com.helen.dnd_charachter_editor.service.reference.table.CharacterClassService;
import com.helen.dnd_charachter_editor.service.reference.table.RaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCharacterService implements CharacterService {

    private final AuthService authService;

    private final RaceService raceService;

    private final CharacterClassService characterClassService;

    private final CharacterAbilityService characterAbilityService;

    private final CharacterWalletService characterWalletService;

    private final CharacterRepository characterRepository;

    private final CharacterAbilityRepository characterAbilityRepository;

    @Override
    public CharacterResponse createCharacter(CreateCharacterRequest createCharacterRequest) {

        User user = authService.getCurrentUser();

        Race race = raceService.getRace(createCharacterRequest.raceId());

        Subrace subrace = raceService.getSubrace(createCharacterRequest.raceId(), createCharacterRequest.subraceId());

        CharacterClass characterClass = characterClassService.getClassById(createCharacterRequest.classID());

        ClassArchetype classArchetype = characterClassService.getClassArchetypeById(
                createCharacterRequest.classID(),
                createCharacterRequest.classArchetypeId()
        );

        UserCharacter userCharacter = CharacterMapper.mapToUserCharacter(
                createCharacterRequest,
                user,
                race,
                subrace,
                characterClass,
                classArchetype
        );

        List<Ability> abilities = characterAbilityRepository.findAllByIds(createCharacterRequest.abilities());



        return null;
    }
}
