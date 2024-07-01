package uniqueproject.uz.go2uzproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uniqueproject.uz.go2uzproject.entity.Agency;
import uniqueproject.uz.go2uzproject.entity.Tour;

import java.util.List;
import java.util.UUID;
@Repository
public interface TourRepository extends JpaRepository<Tour, UUID>, JpaSpecificationExecutor<Tour> {
    List<Tour> findByTitle(String title);

    List<Tour> findByAgencyId(UUID agencyId);

    List<Tour> findAllByAgencyAndRatingIsNotNull(Agency agency);
}
