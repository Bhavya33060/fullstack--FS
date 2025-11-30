package travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel.model.Subscription;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Fetch most recent subscription by email (latest row = active plan)
    Optional<Subscription> findTopByEmailOrderByCreatedAtDesc(String email);

}
