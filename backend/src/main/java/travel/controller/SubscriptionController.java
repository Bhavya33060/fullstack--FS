package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import travel.model.Subscription;
import travel.repository.SubscriptionRepository;

import java.util.*;

@RestController
@RequestMapping("/api/subscribe")
@CrossOrigin(origins = "http://localhost:5173")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    // ✅ CREATE subscription
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Subscription subscription) {
        if (subscription.getReceiptId() == null || subscription.getReceiptId().isEmpty()) {
            subscription.setReceiptId("TF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        Subscription saved = subscriptionRepository.save(subscription);

        Map<String, Object> response = new HashMap<>();
        response.put("receiptId", saved.getReceiptId());
        response.put("receiptUrl", "http://localhost:5173/receipt/" + saved.getReceiptId());
        response.put("plan", saved.getPlan());
        response.put("status", "ok");

        return ResponseEntity.ok(response);
    }

    // ✅ LIST all subscriptions
    @GetMapping
    public ResponseEntity<List<Subscription>> listAll() {
        return ResponseEntity.ok(subscriptionRepository.findAll());
    }

    // ✅ CHECK subscription by email
    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestParam String email) {
        return subscriptionRepository.findTopByEmailOrderByCreatedAtDesc(email)
            .map(sub -> {
                Map<String, Object> response = new HashMap<>();
                response.put("plan", sub.getPlan());   // free, monthly, yearly
                response.put("status", "subscribed");
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> {
                Map<String, Object> response = new HashMap<>();
                response.put("plan", null);            // not subscribed
                response.put("status", "not_subscribed");
                return ResponseEntity.ok(response);
            });
    }
}
