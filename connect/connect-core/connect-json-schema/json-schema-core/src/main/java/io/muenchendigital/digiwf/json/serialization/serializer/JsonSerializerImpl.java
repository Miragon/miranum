package io.muenchendigital.digiwf.json.serialization.serializer;

import io.muenchendigital.digiwf.json.serialization.model.JsonPointer;
import org.everit.json.schema.CombinedSchema;
import org.everit.json.schema.ObjectSchema;
import org.everit.json.schema.Schema;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * JsonSchemaSerializer is an implementation of the JsonSchemaBaseSerializer that provides functionality
 * to serialize and deserialize data based on a json schema.
 */
public class JsonSerializerImpl implements JsonSerializer {

    /**
     * Deserialize data based on the schema.
     * <p>
     * This function compares the keys from data with the keys provided in the json schema
     * and returns all keys from the json schema with the values found in the data map.
     *
     * @param schema
     * @param data
     * @return deserialized data
     */
    @Override
    public Map<String, Object> deserialize(final Schema schema, final Map<String, Object> data) {

        // remove all keys from data that are not in schema
        final Set<String> schemaKeys = this.extractRootKeys(schema);
        final List<String> dataKeys = data.keySet().stream()
                .filter(schemaKeys::contains)
                .collect(Collectors.toList());

        final Map<String, Object> result = new HashMap<>();

        // add available data to the results
        for (final String key : dataKeys) {
            if (data.containsKey(key)) {
                result.put(key, data.get(key));
            }
        }
        return result;
    }

    @Override
    public JSONObject filter(final Schema schema, final JSONObject data, final boolean filterReadOnly) {
        if (schema instanceof ObjectSchema) {
            return this.filter(((ObjectSchema) schema).getPropertySchemas(), data, filterReadOnly);
        }

        final JSONObject result = new JSONObject();

        // combined schemas are saved on the next higher object schema level -> search for the next object schema
        // therefore iterate all sub schemas of the schema and serialize the data for every schema
        // by calling filter(...) recursively
        if (schema instanceof CombinedSchema) {
            final CombinedSchema combinedSchema = (CombinedSchema) schema;
            combinedSchema.getSubschemas().stream()
                    .map(subSchema -> this.filter(subSchema, data, filterReadOnly))
                    .forEach(obj -> obj.keySet().forEach(key -> result.put(key, obj.get(key))));
        }

        return result;
    }

    /**
     * Merge two json objects
     *
     * @param source object that should be merged
     * @param target object to be merged into
     * @return merged object
     */
    @Override
    public Map<String, Object> merge(final JSONObject source, final JSONObject target) {
        return this.deepMerge(source, target).toMap();
    }

    /**
     * Returns all root keys that are in the json schema.
     *
     * @param schema
     * @param filterReadOnly
     * @return
     */
    @Override
    public Set<String> extractRootKeys(final Schema schema, final Boolean filterReadOnly) {
        if (schema instanceof ObjectSchema) {
            final ObjectSchema objectSchema = (ObjectSchema) schema;

            if (filterReadOnly) {
                return objectSchema.getPropertySchemas()
                        .keySet()
                        .stream()
                        .filter(key -> objectSchema.getPropertySchemas().get(key).isReadOnly() != Boolean.TRUE)
                        .collect(Collectors.toSet());
            }

            return new HashSet<>(((ObjectSchema) schema).getPropertySchemas().keySet());
        }

        // if schema is a combined schema iterate all sub schemas and get the keys for every sub schema
        // by calling getKeys(...) recursively
        if (schema instanceof CombinedSchema) {
            final CombinedSchema combinedSchema = (CombinedSchema) schema;
            return combinedSchema.getSubschemas().stream()
                    .map(obj -> this.extractRootKeys(obj, filterReadOnly))
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    /**
     * Returns all root keys that are in the json schema.
     *
     * @param schema
     * @return root keys
     */
    @Override
    public Set<String> extractRootKeys(final Schema schema) {
        return this.extractRootKeys(schema, false);
    }

    /**
     * Extract a value from a json object
     *
     * @param data        data to extract value from
     * @param jsonPointer path to property
     * @return property
     */
    @Override
    public Object extractValue(final JSONObject data, final JsonPointer jsonPointer) {
        return jsonPointer.queryFrom(data);
    }

    /**
     * Generates a json object with value for a given pointer
     *
     * @param jsonPointer pointer in which the value should be inserted
     * @param value       value that sh
     * @return generated value
     */
    @Override
    public JSONObject generateValue(final JsonPointer jsonPointer, final String value) {
        return jsonPointer.generateObjectStructure(value);
    }

    /**
     * Initialize
     *
     * @param keys keys that should be initialized
     * @return
     */
    @Override
    public JSONObject generateObject(final Set<String> keys) {
        final JSONObject jsonObject = new JSONObject();
        keys.forEach(key -> jsonObject.put(key, ""));
        return jsonObject;
    }

    //--------------------------------------------------- helper methods ---------------------------------------------------//

    private JSONObject deepMerge(final JSONObject source, final JSONObject target) {
        for (final String key : source.keySet()) {
            final Object value = source.get(key);
            if (!target.has(key)) {
                //target does not have the same key, should be added to target
                if (value != null && value != JSONObject.NULL) //only add if the source value is not null
                    target.put(key, value);
            } else {
                if (value != null && value != JSONObject.NULL) {
                    if (value instanceof JSONObject) {
                        //source value is json object, start deep merge
                        target.put(key, this.deepMerge((JSONObject) value, this.getObjectOrEmpty(target.get(key))));
                    } else {
                        target.put(key, value);
                    }
                } else {
                    target.remove(key);
                }
            }
        }
        return target;
    }

    private JSONObject getObjectOrEmpty(final Object object) {
        if (object == null | !(object instanceof JSONObject)) {
            return new JSONObject();
        }
        return (JSONObject) object;
    }

    private JSONObject filter(final Map<String, Schema> schema, final JSONObject data, final boolean filterReadOnly) {
        final JSONObject result = new JSONObject();

        // iterate through props and fill them with values
        for (final Map.Entry<String, Schema> entry : schema.entrySet()) {
            // if it is a nested object go recursive
            if (entry.getValue() instanceof ObjectSchema) {
                result.put(entry.getKey(), this.filterObject(this.getDataOrEmptyObject(data, entry.getKey()), entry, filterReadOnly));
            } else if (entry.getValue() instanceof CombinedSchema && !entry.getValue().getUnprocessedProperties().containsKey("fieldType")) {
                final JSONObject obj = this.filter(entry.getValue(), data, filterReadOnly);
                obj.keySet().forEach(key -> result.put(key, obj.get(key)));
            } else {
                if (!filterReadOnly || Boolean.TRUE != entry.getValue().isReadOnly()) {
                    result.put(entry.getKey(), data.has(entry.getKey()) ? data.get(entry.getKey()) : JSONObject.NULL);
                }
            }
        }
        return result;
    }

    private JSONObject filterObject(JSONObject data, final Map.Entry<String, Schema> schema, final boolean filterReadOnly) {
        if (data == null) {
            data = new JSONObject();
        }
        return this.filter(((ObjectSchema) schema.getValue()).getPropertySchemas(), data, filterReadOnly);
    }

    private JSONObject getDataOrEmptyObject(final JSONObject data, final String key) {
        if (!data.has(key) || !(data.get(key) instanceof JSONObject)) {
            return null;
        }
        return (JSONObject) data.get(key);
    }

}
