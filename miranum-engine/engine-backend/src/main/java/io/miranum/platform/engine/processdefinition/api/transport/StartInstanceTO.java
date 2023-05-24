package io.miranum.platform.engine.processdefinition.api.transport;

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
public class StartInstanceTO {

    /**
     * Key of the process that is started.
     */
    private String key;

    /**
     * Variables that are passed to the new process instance.
     */
    private Map<String, Object> variables;

}
