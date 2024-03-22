package io.miranum.platform.connect.json.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonSchemaTestUtils {


    public static String getSchemaString(final String path) throws IOException, URISyntaxException {
        return new String(Files.readAllBytes(Paths.get(JsonSchemaTestUtils.class.getResource(path).toURI())));
    }

}
