package com.helen.dnd_charachter_editor.service.character;

import com.helen.dnd_charachter_editor.dto.request.character.WalletUpdateRequest;
import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;

import java.util.UUID;

public interface CharacterWalletService {

    WalletResponse getWallet(UUID characterId);

    WalletResponse updateWallet(UUID characterId, WalletUpdateRequest request);
}