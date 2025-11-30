package travel.service;

import travel.model.Trip;
import java.util.List;

public interface TripService {
    Trip saveTrip(Trip trip);
    List<Trip> getAllTrips();
    Trip getTrip(Long id);
    void deleteTrip(Long id);
}
