package io.miragon.miranum.platform.connect.message.api;

public interface MessageApi {

    void correlateMessage(CorrelateMessageCommand command);
}
