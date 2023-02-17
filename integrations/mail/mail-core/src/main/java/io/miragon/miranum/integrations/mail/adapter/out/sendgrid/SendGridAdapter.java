package io.miragon.miranum.integrations.mail.adapter.out.sendgrid;

import io.miragon.miranum.integrations.mail.application.port.out.DeliverMailPort;
import io.miragon.miranum.integrations.mail.domain.Mail;
import lombok.extern.java.Log;

@Log
public class SendGridAdapter implements DeliverMailPort {
    @Override
    public void deliverMail(final Mail mail) {
        // TODO: Implement
        log.info("Deliver sendgrid mail:\n" + mail);
    }
}