package io.miranum.platform.connect.message.api;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CorrelateMessageCommand {

    private String messageName;

    private String correlationKey;

    private Map<String, Object> variables;

    public CorrelateMessageCommand(String messageName, String correlationKey) {
        this.messageName = messageName;
        this.correlationKey = correlationKey;
        this.variables = Map.of();
    }
}
