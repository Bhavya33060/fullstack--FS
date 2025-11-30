package travel.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;   
import travel.model.Trip;
import travel.repository.TripRepository;
import travel.service.TripService;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Override
    @Transactional
    public Trip saveTrip(Trip trip) {
        Trip saved = tripRepository.save(trip);
        return tripRepository.findById(saved.getId()).orElse(null);
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public Trip getTrip(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }
}
