package travel.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import travel.model.Travel;
import travel.repository.TravelRepository;
import travel.service.TravelService;

@Service
public class TravelServiceImpl implements TravelService {
    @Autowired
    private TravelRepository travelRepository;

    @Override
    public Travel saveTravel(Travel travel) {
        return travelRepository.save(travel);
    }

    @Override
    public List<Travel> getAllTravels() {
        return travelRepository.findAll();
    }

    @Override
    public Optional<Travel> login(String email, String password) {
        Optional<Travel> user = travelRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
}
