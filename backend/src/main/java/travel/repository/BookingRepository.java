// src/main/java/travel/repository/BookingRepository.java
package travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel.model.Booking;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // 🔍 Find bookings where the city name partially matches (case-insensitive)
    List<Booking> findByCityContainingIgnoreCase(String city);

    // 🔍 Find bookings by exact type (e.g., "stay", "car", "flight")
    List<Booking> findByTypeIgnoreCase(String type);

    // Optional — find bookings that match both city and type
    List<Booking> findByCityContainingIgnoreCaseAndTypeIgnoreCase(String city, String type);
}
