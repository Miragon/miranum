package io.miranum.platform.engine.domain.process;

import io.miranum.platform.engine.domain.jsonschema.JsonSchema;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MiranumProcessDefinitionWithSchema {

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
    private JsonSchema jsonSchema;

    public void setJsonSchema(final JsonSchema jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

}
