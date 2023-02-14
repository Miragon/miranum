package io.muenchendigital.digiwf.json.serialization;

import io.muenchendigital.digiwf.json.serialization.model.JsonPointer;
import io.muenchendigital.digiwf.json.serialization.serializer.JsonSerializer;
import lombok.RequiredArgsConstructor;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.regexp.RE2JRegexpFactory;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Service to handle data serialization and deserialization
 */
@RequiredArgsConstructor
public class JsonSerializationService {

    private final JsonSerializer serializer;

    /**
     * Extract a value from a json object
     *
     * @param schema schema
     * @param data   data to extract value from
     * @return filtered object
     */
    public JSONObject filter(final Map<String, Object> schema, final Map<String, Object> data, final boolean filterReadOnly) {
        final Schema schemaObj = JsonSerializationService.createSchema(new JSONObject(schema));
        return this.serializer.filter(schemaObj, new JSONObject(data), filterReadOnly);
    }

    /**
     * Extract a value from a json object
     *
     * @param schema schema
     * @param data   data to extract value from
     * @return filtered object
     */
    public JSONObject filter(final String schema, final Map<String, Object> data, final boolean filterReadOnly) {
        final Schema schemaObj = JsonSerializationService.createSchema(new JSONObject(schema));
        return this.serializer.filter(schemaObj, new JSONObject(data), filterReadOnly);
    }


    /**
     * Merge two JSON Objects.
     *
     * @param source
     * @param target
     * @return merged data
     */
    public Map<String, Object> merge(final JSONObject source, final JSONObject target) {
        return this.serializer.merge(source, target);
    }

    /**
     * Extract a value from a json object
     *
     * @param data        data to extract value from
     * @param jsonPointer path to property
     * @return property
     */
    public Object extractValue(final Map<String, Object> data, final String jsonPointer) {
        return this.serializer.extractValue(new JSONObject(data), new JsonPointer(jsonPointer));
    }

    /**
     * Generates a json object with value for a given pointer
     *
     * @param jsonPointer pointer in which the value should be inserted
     * @param value       value that sh
     * @return generated value
     */
    public JSONObject generateValue(final String jsonPointer, final String value) {
        return this.serializer.generateValue(new JsonPointer(jsonPointer), value);
    }


    /**
     * Initialize the give schema with default values.
     *
     * @param schema that should be initialized
     * @return data
     */
    public JSONObject initialize(final String schema) {
        final Schema schemaObj = JsonSerializationService.createSchema(new JSONObject(schema));
        final Set<String> rootKeys = this.serializer.extractRootKeys(schemaObj, true);
        return this.serializer.generateObject(rootKeys);
    }

    /**
     * Deserialize data for a specific schema
     *
     * @param schema
     * @param data
     * @return deserialized data
     */
    public Map<String, Object> deserializeData(final Map<String, Object> schema, final Map<String, Object> data) {
        final Schema schemaObject = JsonSerializationService.createSchema(new JSONObject(schema));
        return this.serializer.deserialize(schemaObject, data);
    }

    /**
     * Create and load schema for json schema version draft 7
     *
     * @param schema
     * @return
     */
    public static Schema createSchema(final JSONObject schema) {
        return SchemaLoader.builder().schemaJson(schema)
                .draftV7Support()
                .regexpFactory(new RE2JRegexpFactory())
                .build()
                .load()
                .build();
    }

    /**
     * Create and load schema for json schema version draft 7
     *
     * @param schema
     * @return
     */
    public static Schema createSchema(final String schema) {
        return createSchema(new JSONObject(schema));
    }

}
