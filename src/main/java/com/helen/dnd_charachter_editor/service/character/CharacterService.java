package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.CreateCharacterRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassArchetypeRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterClassRequest;
import com.helen.dnd_charachter_editor.dto.request.character.SetCharacterRaceRequest;
import com.helen.dnd_charachter_editor.dto.response.character.CharacterResponse;

import java.util.UUID;

/**
 * Service contract for character service operations.
 */
public interface CharacterService {
    /**
     * Creates character.
     * @param createCharacterRequest value used by this operation
     * @return result of the operation
     */
    CharacterResponse createCharacter(CreateCharacterRequest createCharacterRequest);

    /**
     * Returns character.
     * @param characterId value used by this operation
     * @return result of the operation
     */
    CharacterResponse getCharacter(UUID characterId);

    /**
     * Updates character.
     * @param characterId value used by this operation
     * @param createCharacterRequest value used by this operation
     * @return result of the operation
     */
    CharacterResponse updateCharacter(UUID characterId, CreateCharacterRequest createCharacterRequest);

    /**
     * Updates character level.
     * @param characterId value used by this operation
     * @param level value used by this operation
     * @return result of the operation
     */
    CharacterResponse updateCharacterLevel(UUID characterId, Integer level);

    /**
     * Updates character health.
     * @param characterId value used by this operation
     * @param maxHealth value used by this operation
     * @param currentHealth value used by this operation
     * @return result of the operation
     */
    CharacterResponse updateCharacterHealth(UUID characterId, Integer maxHealth, Integer currentHealth);

    /**
     * Applies character race.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterResponse applyCharacterRace(UUID characterId, SetCharacterRaceRequest request);

    /**
     * Updates character race.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterResponse updateCharacterRace(UUID characterId, SetCharacterRaceRequest request);

    /**
     * Applies character class.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterResponse applyCharacterClass(UUID characterId, SetCharacterClassRequest request);

    /**
     * Updates character class.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterResponse updateCharacterClass(UUID characterId, SetCharacterClassRequest request);

    /**
     * Applies character class archetype.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterResponse applyCharacterClassArchetype(UUID characterId, SetCharacterClassArchetypeRequest request);

    /**
     * Updates character class archetype.
     * @param characterId value used by this operation
     * @param request value used by this operation
     * @return result of the operation
     */
    CharacterResponse updateCharacterClassArchetype(UUID characterId, SetCharacterClassArchetypeRequest request);

    /**
     * Deletes character.
     * @param characterId value used by this operation
     */
    void deleteCharacter(UUID characterId);
}
