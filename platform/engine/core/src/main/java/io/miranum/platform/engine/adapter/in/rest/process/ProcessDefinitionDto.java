package io.miranum.platform.engine.adapter.in.rest.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transport object of the ServiceDefinition
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionDto {

    /**
     * Key of the process definition.
     */
    private String key;

    /**
     * Name of the process definition.
     */
    private String name;

    /**
     * Description of the process definition.
     */
    private String description;

    /**
     * Versiontag of the process definition.
     */
    private String versionTag;

}
