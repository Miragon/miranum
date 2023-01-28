package io.miragon.miranum.integrations.mail.adapter.out.mail;

import io.miragon.miranum.integrations.mail.application.port.out.DeliverMailPort;
import io.miragon.miranum.integrations.mail.domain.Mail;
import lombok.extern.java.Log;

@Log
public class JavaMailAdapter implements DeliverMailPort {
    @Override
    public void deliverMail(final Mail mail) {
        log.info("deliver java mail");
    }
}
