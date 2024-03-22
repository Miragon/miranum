package io.miranum.platform.connect.json.impl;

import com.networknt.schema.*;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class JsonSchemaFactory {

    public static io.miranum.platform.connect.json.api.JsonSchema createJsonSchema(final String rawSchema) {

        final JsonMetaSchema metaSchema = new MirnaumJsonSchemaVersion().getInstance();

        final com.networknt.schema.JsonSchema schema = com.networknt.schema.JsonSchemaFactory.builder()
                .defaultMetaSchemaURI(metaSchema.getUri())
                .addMetaSchema(metaSchema)
                .build()
                .getSchema(rawSchema);

        return new JsonSchemaImpl(schema);
    }

    static class MirnaumJsonSchemaVersion extends JsonSchemaVersion {
        private static final String URI = "https://json-schema.org/draft-07/schema";
        private static final String ID = "$id";

        static {
            // add version specific formats here.
            //BUILTIN_FORMATS.add(pattern("phone", "^\\+(?:[0-9] ?){6,14}[0-9]$"));
        }

        @Override
        public JsonMetaSchema getInstance() {
            return new JsonMetaSchema.Builder(URI)
                    .idKeyword(ID)
                    .addFormats(BUILTIN_FORMATS)
                    .addKeywords(ValidatorTypeCode.getNonFormatKeywords(SpecVersion.VersionFlag.V7))
                    // keywords that may validly exist, but have no validation aspect to them
                    .addKeywords(Arrays.asList(
                            new NonValidationKeyword("$schema"),
                            new NonValidationKeyword("$id"),
                            new NonValidationKeyword("title"),
                            new NonValidationKeyword("description"),
                            new NonValidationKeyword("default"),
                            new NonValidationKeyword("definitions"),
                            new NonValidationKeyword("$comment"),
                            new NonValidationKeyword("contentMediaType"),
                            new NonValidationKeyword("contentEncoding"),
                            new NonValidationKeyword("examples"),
                            new NonValidationKeyword("message")
                    ))
                    .addKeyword(new ReadOnlyKeyword())
                    .addKeyword(new AllOfKeyword())
                    .build();
        }
    }
}
