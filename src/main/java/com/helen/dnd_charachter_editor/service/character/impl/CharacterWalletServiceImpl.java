package com.helen.dnd_charachter_editor.service.character.impl;

import com.helen.dnd_charachter_editor.dto.request.character.WalletUpdateRequest;
import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterWallet;
import com.helen.dnd_charachter_editor.mapper.character.CharacterWalletMapper;
import com.helen.dnd_charachter_editor.repository.CharacterRepository;
import com.helen.dnd_charachter_editor.repository.character.CharacterWalletRepository;
import com.helen.dnd_charachter_editor.service.character.CharacterWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CharacterWalletServiceImpl implements CharacterWalletService {

    private final CharacterWalletRepository characterWalletRepository;
    private final CharacterRepository characterRepository;
    private final CharacterWalletMapper characterWalletMapper;

    @Override
    public WalletResponse getWallet(UUID characterId) {
        checkCharacterExists(characterId);

        CharacterWallet wallet = getOrCreateWallet(characterId);

        return characterWalletMapper.toResponse(wallet);
    }

    @Override
    public WalletResponse updateWallet(UUID characterId, WalletUpdateRequest request) {
        checkCharacterExists(characterId);

        CharacterWallet wallet = getOrCreateWallet(characterId);

        if (request.copper() != null) {
            wallet.setCopper(request.copper());
        }

        if (request.silver() != null) {
            wallet.setSilver(request.silver());
        }

        if (request.electrum() != null) {
            wallet.setElectrum(request.electrum());
        }

        if (request.gold() != null) {
            wallet.setGold(request.gold());
        }

        if (request.platinum() != null) {
            wallet.setPlatinum(request.platinum());
        }

        CharacterWallet savedWallet = characterWalletRepository.save(wallet);

        return characterWalletMapper.toResponse(savedWallet);
    }

    private CharacterWallet getOrCreateWallet(UUID characterId) {
        return characterWalletRepository.findByCharacterId(characterId)
                .orElseGet(() -> createEmptyWallet(characterId));
    }

    private CharacterWallet createEmptyWallet(UUID characterId) {
        CharacterWallet wallet = CharacterWallet.builder()
                .characterId(characterId)
                .copper(0)
                .silver(0)
                .electrum(0)
                .gold(0)
                .platinum(0)
                .build();

        return characterWalletRepository.save(wallet);
    }

    private void checkCharacterExists(UUID characterId) {
        if (!characterRepository.existsByCharacterId(characterId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Character with id " + characterId + " not found"
            );
        }
    }
}