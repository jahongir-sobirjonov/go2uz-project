package uniqueproject.uz.go2uzproject.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.exception.SendVerificationCodeException;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;
    public static final Random random = new Random();

    public String sendVerificationCode(String email) {

        try {
            String  verificationCode = String.valueOf(random.nextInt(90000) + 10000);
            String message = "This is your verification code: " + verificationCode;
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText(message);
            mailSender.send(simpleMailMessage);
            return verificationCode;
        } catch (Exception e) {
            throw new SendVerificationCodeException("Error to send the verification code, because email invalid");
        }

    }

    public void sendPasswordResetEmail(String email, String token) {
        try {
            String resetUrl = "http://yourdomain.com/api/password-reset/reset?token=" + token;
            String message = "Click the link to reset your password: " + resetUrl;
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject("Your password reset link");
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(email);
            simpleMailMessage.setText(message);
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            throw new SendVerificationCodeException("Error sending password reset email");
        }
    }




}