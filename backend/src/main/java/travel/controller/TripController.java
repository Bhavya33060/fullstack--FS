package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.model.Trip;
import travel.service.TripService;

import java.util.List;

@RestController
@RequestMapping("/trip")
@CrossOrigin(origins = "http://localhost:5173")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping("/create")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) {
        Trip saved = tripService.saveTrip(trip);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    /** ✅ GET All Trips */
    @GetMapping("/all")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    /** ✅ GET Trip By ID */
    @GetMapping("/{id}")
    public Trip getTrip(@PathVariable Long id) {
        return tripService.getTrip(id);
    }

    /** ✅ DELETE Trip */
    @DeleteMapping("/{id}")
    public String deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return "Trip deleted ✅";
    }
}
