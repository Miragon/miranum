package io.miragon.miranum.platform.adapter.in.rest.process;

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
     * Process Definition Key
     */
    private String key;

    /**
     * Unique Key for Process Instance
     */
    private String correlationKey;

    /**
     * Variables that are passed to the new process instance.
     */
    private Map<String, Object> variables;

}
