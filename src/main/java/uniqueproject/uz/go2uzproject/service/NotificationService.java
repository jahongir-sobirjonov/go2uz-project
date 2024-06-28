package uniqueproject.uz.go2uzproject.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.entity.Agency;
import uniqueproject.uz.go2uzproject.entity.Notification;
import uniqueproject.uz.go2uzproject.entity.Order;
import uniqueproject.uz.go2uzproject.entity.UserEntity;
import uniqueproject.uz.go2uzproject.repository.NotificationRepository;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;



    public void notifyAgency(Order order) {
        String subject = "New Booking Request";
        String message = "New booking request for tour: " + order.getTour().getTitle();
        sendEmail(order.getTour().getAgency().getOwner().getEmail(), subject, message);
        saveNotification(message, order.getTour().getAgency().getOwner(), order.getTour().getAgency(), order);
    }

    public void notifyUser(Order order) {
        String subject = "Booking Status Update";
        String message = "Your booking for tour: " + order.getTour().getTitle() +
                " has been " + order.getStatus().name().toLowerCase();
        sendEmail(order.getUser().getEmail(), subject, message);
        saveNotification(message, order.getUser(), order.getTour().getAgency(), order);
    }

    private void sendEmail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exception gracefully
        }
        mailSender.send(message);
    }

    private void saveNotification(String message, UserEntity user, Agency agency, Order order) {
        Notification notification = Notification.builder()
                .message(message)
                .user(user)
                .agency(agency)
                .createdDate(LocalDateTime.now())
                .order(order)
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
