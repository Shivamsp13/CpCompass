package com.cpcompass.controller;

import com.cpcompass.dto.journal.CreateJournalRequest;
import com.cpcompass.dto.journal.JournalResponse;
import com.cpcompass.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
@RequiredArgsConstructor
public class JournalController {

    private final JournalService journalService;

    @PostMapping
    public JournalResponse createEntry(
            @RequestBody CreateJournalRequest request
    ) {

        return journalService.createEntry(
                request
        );
    }

    @PutMapping("/{id}")
    public JournalResponse updateEntry(
            @PathVariable Long id,
            @RequestBody CreateJournalRequest request
    ) {

        return journalService.updateEntry(
                id,
                request
        );
    }

    @GetMapping
    public List<JournalResponse> getAllEntries() {

        return journalService.getAllEntries();
    }

    @GetMapping("/{id}")
    public JournalResponse getEntryById(
            @PathVariable Long id
    ) {

        return journalService.getEntryById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEntry(
            @PathVariable Long id
    ) {

        journalService.deleteEntry(id);
    }
}