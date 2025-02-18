package io.miragon.miranum.platform.example.adapter.out.email;

import io.miragon.miranum.platform.example.application.domain.SendNotification;
import io.miragon.miranum.platform.example.application.port.out.SendNotificationOutPort;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class EmailAdapter implements SendNotificationOutPort {

    private final JavaMailSender mailSender;

    public EmailAdapter() throws jakarta.mail.MessagingException {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        final int timeout = 30000; // 30 seconds
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.connectiontimeout", timeout);
        properties.put("mail.smtp.timeout", timeout);
        properties.put("mail.smtp.writetimeout", timeout);

        mailSender.testConnection();

        this.mailSender = mailSender;
    }


    @Override
    public void notify(final SendNotification sendNotification) {
        try {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

            mimeMessage.setFrom(new InternetAddress("no-reply@miragon.io"));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.join(",", sendNotification.receivers())));
            mimeMessage.setSubject(sendNotification.subject());
            mimeMessage.setText(sendNotification.body());

            this.mailSender.send(mimeMessage);
        } catch (final MessagingException e) {
            log.error("Error sending email", e);
        }
    }
}
