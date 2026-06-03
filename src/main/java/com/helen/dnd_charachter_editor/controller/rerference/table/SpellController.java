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
 * Контроллер REST API для обработки запросов `SpellController`.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/spells")
public class SpellController {

    private final SpellService spellService;

    /**
     * Возвращает данные для запрошенной операции.
     * @return результат выполнения операции
     */
    @GetMapping
    public List<SpellResponse> getAllSpells() {
        return spellService.getAllSpells();
    }

    /**
     * Возвращает данные для запрошенной операции.
     * @param spellId параметр, используемый при выполнении операции
     * @return результат выполнения операции
     */
    @GetMapping("/{spellId}")
    public SpellResponse getSpellById(@PathVariable UUID spellId) {
        return spellService.getSpellResponse(spellId);
    }
}
