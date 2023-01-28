package io.miragon.miranum.integrations.mail.application.port.in;

import lombok.Getter;

@Getter
public class SendMailCommand {

    private String text;

    private String subject;

    private String recipient;

}
