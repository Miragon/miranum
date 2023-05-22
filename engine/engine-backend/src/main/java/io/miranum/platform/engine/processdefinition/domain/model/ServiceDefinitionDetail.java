package io.miranum.platform.engine.processdefinition.domain.model;

import lombok.*;

import java.util.Map;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ServiceDefinitionDetail {

    /**
     * Id of the process definition.
     */
    private final String id;
    /**
     * Key of the process definition.
     */
    private final String key;
    /**
     * Name of the process definition.
     */
    private final String name;
    /**
     * Description provides further information about the process definition.
     */
    private final String description;
    /**
     * Versiontag of the process definition.
     */
    private final String versionTag;

    /**
     * json schema
     */
    private Map<String, Object> jsonSchema;

    public void setJsonSchema(final Map<String, Object> jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

}
