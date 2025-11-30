package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.model.SavedItem;
import travel.repository.SavedItemRepository;
import java.util.*;

@RestController
@RequestMapping("/api/saved")
@CrossOrigin(origins = "http://localhost:5173")
public class SavedItemController {

    @Autowired
    private SavedItemRepository savedItemRepository;

    // Get all saved items
    @GetMapping
    public ResponseEntity<List<SavedItem>> getAllSaved() {
        return ResponseEntity.ok(savedItemRepository.findAll());
    }

    // Add new saved item
    @PostMapping
    public ResponseEntity<SavedItem> addSaved(@RequestBody SavedItem item) {
        item.setSavedAt(java.time.LocalDateTime.now());
        SavedItem saved = savedItemRepository.save(item);
        return ResponseEntity.ok(saved);
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaved(@PathVariable Long id) {
        if (!savedItemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        savedItemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Optional: clear all
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        savedItemRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
