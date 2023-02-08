package io.miragon.miranum.connect.process.application.port.in;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StartProcessCommand {

    private String processKey;
    private Map<String, Object> variables;
}
