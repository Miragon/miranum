package io.miragon.miranum.connect.message.application.port.in;

public interface CorrelateMessageUseCase {

    void correlateMessage(CorrelateMessageCommand command);
}
