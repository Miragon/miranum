package io.miragon.miranum.integrations.mail.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SendMailCommand {

    private String recipient;
    private String subject;
    private String text;
}
