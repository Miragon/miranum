package io.miragon.miranum.platform.connect.process.api;

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
