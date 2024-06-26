package io.miragon.miranum.platform.application;

import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.platform.application.port.in.CustomerMailCommand;
import io.miragon.miranum.platform.application.port.in.MailReceived;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HandleCustomerMailUseCase implements MailReceived {
    private final MessageApi messageApi;

    private static final String MAIL_ACCEPTED_MESSAGE_NAME = "";
    private static final String MAIL_DECLINED_MESSAGE_NAME = "";

    @Override
    public void handle(CustomerMailCommand command) {

    }
}
