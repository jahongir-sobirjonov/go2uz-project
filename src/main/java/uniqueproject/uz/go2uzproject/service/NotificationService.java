package uniqueproject.uz.go2uzproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.entity.Agency;
import uniqueproject.uz.go2uzproject.entity.Notification;
import uniqueproject.uz.go2uzproject.entity.Order;
import uniqueproject.uz.go2uzproject.entity.UserEntity;
import uniqueproject.uz.go2uzproject.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;


    public void notifyAgency(Order order) {
        String message = "New booking request for tour: " + order.getTour().getTitle();
        Notification notification = Notification.builder()
                .message(message)
                .agency(order.getTour().getAgency())
                .createdDate(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

    public void notifyUser(Order order) {
        String message = "Your booking for tour: " + order.getTour().getTitle() + " has been " + order.getStatus().name().toLowerCase();
        Notification notification = Notification.builder()
                .message(message)
                .user(order.getUser())
                .agency(order.getTour().getAgency())
                .createdDate(LocalDateTime.now())
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForUser(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notification> getNotificationsForAgency(UUID agencyId) {
        return notificationRepository.findByAgencyId(agencyId);
    }


}
