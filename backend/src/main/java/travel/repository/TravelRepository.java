package travel.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import travel.model.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {
    
    // Find a travel user by email
    Optional<Travel> findByEmail(String email);

    // Check if an email already exists (for signup validation)
    boolean existsByEmail(String email);
}
