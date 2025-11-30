package travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel.model.Guide;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, String> {

    // Find by status (published / pending / rejected)
    List<Guide> findByStatusIgnoreCase(String status);

    // Simple search: title or excerpt or location (case-insensitive)
    List<Guide> findByTitleContainingIgnoreCaseOrExcerptContainingIgnoreCaseOrLocationContainingIgnoreCase(String t, String e, String l);

    // Find guides that contain a tag - JPA cannot automatically search element collections by "containsIgnoreCase" so use simple match:
    List<Guide> findByTagsContaining(String tag);
}
