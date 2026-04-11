package pl.edu.uj.tp.nexo.invitation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${application.frontend-inventation-url}")
    private String frontendUrl;

    public void sendInvitationEmail(String to, String token, String organizationName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("You are invited to join " + organizationName + " on Nexo!");

        message.setText("You have been invited to join the organization: " + organizationName + ".\n\n" +
                "Click the following link to register: " + frontendUrl + "/register?token=" + token);

        mailSender.send(message);
    }
}
