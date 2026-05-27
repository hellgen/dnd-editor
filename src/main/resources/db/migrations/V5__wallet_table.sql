CREATE TABLE IF NOT EXISTS dnd_editor.character_wallets
(
    character_wallet_id UUID PRIMARY KEY,
    character_id        UUID NOT NULL UNIQUE,

    copper              INT NOT NULL DEFAULT 0 CHECK (copper >= 0),
    silver              INT NOT NULL DEFAULT 0 CHECK (silver >= 0),
    electrum            INT NOT NULL DEFAULT 0 CHECK (electrum >= 0),
    gold                INT NOT NULL DEFAULT 0 CHECK (gold >= 0),
    platinum            INT NOT NULL DEFAULT 0 CHECK (platinum >= 0),

    CONSTRAINT fk_character_wallet_character
        FOREIGN KEY (character_id)
            REFERENCES dnd_editor.characters (character_id)
            ON DELETE CASCADE
);