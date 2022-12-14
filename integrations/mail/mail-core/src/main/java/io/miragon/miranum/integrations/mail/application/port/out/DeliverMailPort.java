package io.miragon.miranum.integrations.mail.application.port.out;

import io.miragon.miranum.integrations.mail.domain.Mail;

public interface DeliverMailPort {

    void deliverMail(Mail mail);

}
