package io.miragon.miranum.connect.binder.adapter.kafka;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CorrelateMessageEvent {

    private String processInstanceId;

    @NotBlank
    private String messageName;

    private String businessKey;

    private Map<String, Object> payloadVariables;

}
