package uniqueproject.uz.go2uzproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniqueproject.uz.go2uzproject.entity.Review;
import uniqueproject.uz.go2uzproject.entity.UserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByTourId(UUID tourId);

    boolean existsByAuthor(UserEntity author);
}
