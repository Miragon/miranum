package io.miragon.miranum.examples.process.c7;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StartProcessRequestDto {

    private String processKey;
    private Map<String, Object> variables;
}
