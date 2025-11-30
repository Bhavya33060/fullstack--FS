package travel.service;

import java.util.List;
import java.util.Optional;

import travel.model.Travel;

public interface TravelService{
	
	public Travel saveTravel(Travel Travel);
	public List<Travel> getAllTravels();
	
	  public Optional<Travel> login(String email, String password);
}