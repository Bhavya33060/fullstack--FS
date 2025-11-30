package travel.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import travel.model.Travel;
import travel.repository.TravelRepository;
import travel.service.TravelService;

@RestController
@RequestMapping("/Travel")
@CrossOrigin(origins = "http://localhost:5173")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @Autowired
    private TravelRepository travelRepository;

    // ✅ Signup / Register
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Travel travel) {
        if (travelRepository.existsByEmail(travel.getEmail())) {
            return ResponseEntity.status(400).body(Map.of("error", "Email already registered ❌"));
        }

        travelService.saveTravel(travel);
        return ResponseEntity.ok(Map.of("message", "NEW CUSTOMER IS ADDED ✅"));
    }

    // ✅ Get all users
    @GetMapping("/getAll")
    public List<Travel> getAllTravels() {
        return travelService.getAllTravels();
    }

    // ✅ Login (fixed to return JSON object, not string)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Travel loginRequest) {
        Optional<Travel> user = travelRepository.findByEmail(loginRequest.getEmail());

        // ❌ No user found
        if (user.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "User not found"));
        }

        Travel existingUser = user.get();

        // ❌ Wrong password
        if (!existingUser.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
        }

        // ✅ Remove password before sending to frontend
        existingUser.setPassword(null);

        // ✅ Send user JSON back to frontend (so it can store in localStorage)
        return ResponseEntity.ok(existingUser);
    }
}
