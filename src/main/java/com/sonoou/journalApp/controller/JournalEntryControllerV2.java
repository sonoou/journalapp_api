package com.sonoou.journalApp.controller;

import com.sonoou.journalApp.entity.JournalEntry;
import com.sonoou.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping  // localhost:8080/journal -> GET call
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping // localhost:8080/journal -> POST call
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry updateEntry) {
        JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(updateEntry.getTitle() != null && !updateEntry.getTitle().equals("") ? updateEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(updateEntry.getContent() != null && !updateEntry.getContent().equals("") ? updateEntry.getContent() : oldEntry.getContent());
        }

        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }
}

