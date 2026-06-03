package com.helen.dnd_charachter_editor.controller.rerference.table;

import com.helen.dnd_charachter_editor.dto.response.reference.table.SpellResponse;
import com.helen.dnd_charachter_editor.service.reference.table.SpellService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST controller that exposes spell reference endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/spells")
public class SpellController {

    private final SpellService spellService;

    /**
     * Returns all spells.
     * @return result of the operation
     */
    @GetMapping
    public List<SpellResponse> getAllSpells() {
        return spellService.getAllSpells();
    }

    /**
     * Returns spell by id.
     * @param spellId value used by this operation
     * @return result of the operation
     */
    @GetMapping("/{spellId}")
    public SpellResponse getSpellById(@PathVariable UUID spellId) {
        return spellService.getSpellResponse(spellId);
    }
}
