package uniqueproject.uz.go2uzproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uniqueproject.uz.go2uzproject.entity.Agency;
import uniqueproject.uz.go2uzproject.entity.Notification;
import uniqueproject.uz.go2uzproject.entity.UserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserId(UUID user_id);
    List<Notification> findByAgencyId(UUID agency_id);

}
