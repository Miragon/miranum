package io.muenchendigital.digiwf.json.utils;

import io.muenchendigital.digiwf.json.factory.JsonSchemaFactory;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonSchemaTestUtils {


    public static String getSchemaString(final String path) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(JsonSchemaTestUtils.class.getResource(path).toURI())));
    }

    public static boolean areEqual(final Map<String, Object> first, final Map<String, Object> second) {
        if (first.size() != second.size()) {
            return false;
        }

        return first.entrySet().stream()
                .allMatch(e -> compareObject(e.getValue(), second.get(e.getKey())));
    }

    public static boolean compareObject(final Object obj1, final Object obj2) {
        if (obj1 instanceof JSONObject) {
            return obj1.toString().equals(obj2.toString());
        }

        return obj1.equals(obj2);
    }

    public static Map<String, Object> getSchemaMap(final String schemaString) {
        return JsonSchemaFactory.gson().fromJson(schemaString, JsonSchemaFactory.mapType());
    }

}
