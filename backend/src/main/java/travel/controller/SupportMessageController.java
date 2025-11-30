package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import travel.model.SupportMessage;
import travel.repository.SupportMessageRepository;

import java.util.List;

@RestController
@RequestMapping("/api/support")
@CrossOrigin(origins = "http://localhost:5173")
public class SupportMessageController {

    @Autowired
    private SupportMessageRepository repo;

    @PostMapping
    public ResponseEntity<SupportMessage> create(@RequestBody SupportMessage m) {
        SupportMessage saved = repo.save(m);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<SupportMessage>> list() {
        return ResponseEntity.ok(repo.findAll());
    }
}
