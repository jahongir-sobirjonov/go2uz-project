package uniqueproject.uz.go2uzproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniqueproject.uz.go2uzproject.entity.Agency;

import java.util.List;
import java.util.UUID;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, UUID> {
    List<Agency> findByOwnerId(UUID ownerId);


    boolean existsByName(String name);
}
