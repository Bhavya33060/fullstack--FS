package travel.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import travel.repository.BookingRepository;
import travel.model.Booking;

import java.util.*;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173") // allow Vite dev server; adjust for prod
public class SearchController {

    @Autowired
    private BookingRepository bookingRepository;

    // Simple search: ?tab=stays&city=Goa
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> search(
            @RequestParam(required = false) String from,
            @RequestParam(required = false, defaultValue = "flights") String tab,
            @RequestParam(required = false) String city
    ) {
        // If tab === stays/cars/packages we fetch from bookings or return sample data.
        if ("stays".equalsIgnoreCase(tab) || "cars".equalsIgnoreCase(tab) || "packages".equalsIgnoreCase(tab)) {
            List<Booking> items;
            if (city != null && !city.isBlank()) {
                items = bookingRepository.findByCityContainingIgnoreCase(city);
            } else {
                items = bookingRepository.findAll();
            }
            List<Map<String,Object>> resp = new ArrayList<>();
            for (Booking b : items) {
                Map<String,Object> m = new HashMap<>();
                m.put("id", "B" + (b.getBookingId() != null ? b.getBookingId() : UUID.randomUUID().toString()));
                m.put("kind", b.getType() != null ? b.getType() : "stays"); // stay/car/flight
                m.put("city", b.getCity() != null ? b.getCity() : "Unknown");
                m.put("title", (b.getType() != null ? b.getType() : "Stay") + " in " + (b.getCity() != null ? b.getCity() : "Unknown"));
                m.put("price", "$" + (b.getSubtotal() != null ? b.getSubtotal().toString() : "0"));
                // latitude/longitude fallback if null
                m.put("lat", b.getLatitude() != null ? b.getLatitude() : 9.9312);
                m.put("lng", b.getLongitude() != null ? b.getLongitude() : 76.2673);
                // extra optional fields for frontend
                m.put("stops", null);
                m.put("category", "any");
                m.put("airline", null);
                m.put("rating", 4.2);
                resp.add(m);
            }
            return ResponseEntity.ok(resp);
        }

        // flights & packages: return simple generated sample data
        List<Map<String,Object>> sample = new ArrayList<>();
        sample.add(Map.of(
            "id","F001","kind","flights","city","Kochi","title","Kochi — City break","price","$199","lat",9.9312,"lng",76.2673
        ));
        sample.add(Map.of(
            "id","F002","kind","flights","city","Bali","title","Bali — Island escape","price","$399","lat",-8.4095,"lng",115.1889
        ));
        return ResponseEntity.ok(sample);
    }
}
