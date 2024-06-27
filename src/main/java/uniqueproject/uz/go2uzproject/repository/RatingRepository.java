package uniqueproject.uz.go2uzproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniqueproject.uz.go2uzproject.entity.Rating;
import uniqueproject.uz.go2uzproject.entity.Tour;

import java.util.List;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    List<Rating> findByTourId(UUID tourId);

    boolean existsByAuthorIdAndTour(UUID authorId, Tour tour);
}
