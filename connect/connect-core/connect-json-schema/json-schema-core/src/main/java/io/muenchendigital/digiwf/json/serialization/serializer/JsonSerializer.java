package io.muenchendigital.digiwf.json.serialization.serializer;

import io.muenchendigital.digiwf.json.serialization.model.JsonPointer;
import org.everit.json.schema.Schema;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * JsonSchemaBaseSerializer is an interface which is used in the JsonSchemaSerializationService to serialize and deserialize data based on a json schema.
 */
public interface JsonSerializer {

    /**
     * Deserialize the data according to the provided json schema.
     *
     * @param schema
     * @param data
     * @return
     */
    Map<String, Object> deserialize(final Schema schema, final Map<String, Object> data);

    /**
     * Filter data and readOnly values
     *
     * @param schema         Json Schema structure
     * @param data           data that is filtered
     * @param filterReadOnly filter readOnly values
     * @return filtered values
     */
    JSONObject filter(final Schema schema, final JSONObject data, boolean filterReadOnly);

    /**
     * Merge two JSON Objects.
     *
     * @param source
     * @param target
     * @return merged data
     */
    Map<String, Object> merge(final JSONObject source, JSONObject target);

    /**
     * Returns all root keys that are in the json schema.
     *
     * @param schema
     * @return root keys
     */
    Set<String> extractRootKeys(final Schema schema);


    /**
     * Returns all root keys that are in the json schema.
     *
     * @param schema
     * @param filterReadOnly
     * @return root keys
     */
    Set<String> extractRootKeys(final Schema schema, Boolean filterReadOnly);

    /**
     * Extract a value from a json object
     *
     * @param data        data to extract value from
     * @param jsonPointer path to property
     * @return property
     */
    Object extractValue(final JSONObject data, final JsonPointer jsonPointer);

    /**
     * Generates a json object with value for a given pointer
     *
     * @param jsonPointer pointer in which the value should be inserted
     * @param value       value that sh
     * @return generated value
     */
    JSONObject generateValue(final JsonPointer jsonPointer, final String value);

    /**
     * Generates a json object and initializes keys with empty strings
     *
     * @return generated value
     */
    JSONObject generateObject(Set<String> keys);
}

