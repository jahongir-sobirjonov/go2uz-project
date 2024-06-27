package uniqueproject.uz.go2uzproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uniqueproject.uz.go2uzproject.entity.Notification;
import uniqueproject.uz.go2uzproject.service.NotificationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/get-notification-for-user{userId}")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    @GetMapping("/get-notification-for-agency{agencyId}")
    public ResponseEntity<List<Notification>> getNotificationsForAgency(@PathVariable UUID agencyId) {
        return ResponseEntity.ok(notificationService.getNotificationsForAgency(agencyId));
    }
}
