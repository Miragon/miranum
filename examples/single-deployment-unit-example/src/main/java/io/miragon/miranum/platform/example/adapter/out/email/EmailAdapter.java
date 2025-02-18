package io.miragon.miranum.platform.example.adapter.out.email;

import io.miragon.miranum.platform.example.application.domain.SendNotification;
import io.miragon.miranum.platform.example.application.port.out.SendNotificationOutPort;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
@EnableConfigurationProperties(EmailProperties.class)
public class EmailAdapter implements SendNotificationOutPort {

    public static final int TIMEOUT = 30000;
    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    public EmailAdapter(final EmailProperties emailProperties) throws jakarta.mail.MessagingException {
        this.emailProperties = emailProperties;

        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setProtocol(emailProperties.getProtocol());
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(emailProperties.getPassword());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.connectiontimeout", TIMEOUT);
        properties.put("mail.smtp.timeout", TIMEOUT);
        properties.put("mail.smtp.writetimeout", TIMEOUT);

        mailSender.testConnection();

        this.mailSender = mailSender;
    }


    @Override
    public void notify(final SendNotification sendNotification) {
        try {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();

            mimeMessage.setFrom(new InternetAddress(emailProperties.getFrom()));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.join(",", sendNotification.receivers())));
            mimeMessage.setSubject(sendNotification.subject());
            mimeMessage.setText(sendNotification.body());

            this.mailSender.send(mimeMessage);
        } catch (final MessagingException e) {
            log.error("Error sending email", e);
        }
    }
}
