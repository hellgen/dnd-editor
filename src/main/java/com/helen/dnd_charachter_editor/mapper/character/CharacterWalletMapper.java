package com.helen.dnd_charachter_editor.mapper.character;

import com.helen.dnd_charachter_editor.dto.response.character.WalletResponse;
import com.helen.dnd_charachter_editor.entity.character.CharacterWallet;


public class CharacterWalletMapper {

    public static WalletResponse toResponse(CharacterWallet wallet) {
        return WalletResponse.builder()
                .characterWalletId(wallet.getId())
                .characterId(wallet.getCharacterId())
                .copper(wallet.getCopper())
                .silver(wallet.getSilver())
                .electrum(wallet.getElectrum())
                .gold(wallet.getGold())
                .platinum(wallet.getPlatinum())
                .build();
    }
}