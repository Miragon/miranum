package io.miragon.miranum.connect.message.api;

public interface CorrelateMessageUseCase {

    void correlateMessage(CorrelateMessageCommand command);
}
