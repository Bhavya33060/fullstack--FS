package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import travel.model.Guide;
import travel.repository.GuideRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/guides")
@CrossOrigin(origins = "http://localhost:5173")
public class GuideController {

    @Autowired
    private GuideRepository guideRepository;

    @GetMapping
    public ResponseEntity<List<Guide>> listAll(@RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(guideRepository.findByStatusIgnoreCase(status));
        }
        return ResponseEntity.ok(guideRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guide> getOne(@PathVariable String id) {
        return guideRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Guide> create(@RequestBody Guide guide) {

        if (guide.getId() == null || guide.getId().isBlank()) {
            guide.setId(UUID.randomUUID().toString());
        }
        if (guide.getStatus() == null || guide.getStatus().isBlank()) {
            guide.setStatus("pending");
        }
        if (guide.getCreatedAt() == null) {
            guide.setCreatedAt(LocalDateTime.now());
        }
        if (guide.getFavorite() == null) guide.setFavorite(false);
        if (guide.getTags() == null) guide.setTags(new ArrayList<>());

        Guide saved = guideRepository.save(guide);
        return ResponseEntity.created(URI.create("/api/guides/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Guide> update(@PathVariable String id, @RequestBody Guide payload) {
        return guideRepository.findById(id).map(existing -> {

            existing.setTitle(payload.getTitle());
            existing.setLocation(payload.getLocation());
            existing.setExcerpt(payload.getExcerpt());
            existing.setThumbnail(payload.getThumbnail());
            existing.setHero(payload.getHero());
            existing.setAuthor(payload.getAuthor());
            existing.setDuration(payload.getDuration());
            existing.setRating(payload.getRating());
            existing.setFavorite(payload.getFavorite());
            existing.setDirections(payload.getDirections());
            existing.setCoordsLat(payload.getCoordsLat());
            existing.setCoordsLng(payload.getCoordsLng());
            existing.setTags(payload.getTags());
            existing.setStatus(payload.getStatus());

            return ResponseEntity.ok(guideRepository.save(existing));

        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/favorite")
    public ResponseEntity<Guide> toggleFavorite(@PathVariable String id, @RequestParam boolean fav) {
        return guideRepository.findById(id).map(g -> {
            g.setFavorite(fav);
            return ResponseEntity.ok(guideRepository.save(g));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!guideRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        guideRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Guide>> search(@RequestParam String q) {
        if (q == null || q.isBlank()) return ResponseEntity.ok(Collections.emptyList());
        return ResponseEntity.ok(
                guideRepository.findByTitleContainingIgnoreCaseOrExcerptContainingIgnoreCaseOrLocationContainingIgnoreCase(q, q, q)
        );
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<Guide>> byTag(@PathVariable String tag) {
        String lc = tag.toLowerCase(Locale.ROOT);
        return ResponseEntity.ok(
                guideRepository.findAll().stream()
                        .filter(g -> g.getTags().stream()
                                .anyMatch(t -> t.toLowerCase(Locale.ROOT).equals(lc)))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Guide> approve(@PathVariable String id) {
        return guideRepository.findById(id).map(g -> {
            g.setStatus("published");
            return ResponseEntity.ok(guideRepository.save(g));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Guide> reject(@PathVariable String id) {
        return guideRepository.findById(id).map(g -> {
            g.setStatus("rejected");
            return ResponseEntity.ok(guideRepository.save(g));
        }).orElse(ResponseEntity.notFound().build());
    }
}
