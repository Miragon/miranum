package io.miragon.miranum.connect.binder.message.application.port.in;

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

}
