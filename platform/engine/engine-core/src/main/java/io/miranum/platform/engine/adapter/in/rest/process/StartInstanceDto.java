package io.miranum.platform.engine.adapter.in.rest.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Transport object to start a new process instance.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StartInstanceDto {

    /**
     * Key of the process that is started.
     */
    private String key;

    /**
     * Variables that are passed to the new process instance.
     */
    private Map<String, Object> variables;

}
