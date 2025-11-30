package travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
