package io.muenchendigital.digiwf.json.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.ToNumberStrategy;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class JsonSchemaFactory {

    public static Gson gson() {
        return new GsonBuilder()
                .setNumberToNumberStrategy(new IntegerOrDoubleStrategy())
                .setObjectToNumberStrategy(new IntegerOrDoubleStrategy())
                .create();
    }

    public static Type mapType() {
        return new TypeToken<Map<String, Object>>() {
        }.getType();
    }

    static class IntegerOrDoubleStrategy implements ToNumberStrategy {

        public Number readNumber(final JsonReader in) throws IOException, JsonParseException {
            final String value = in.nextString();

            try {
                final Double d = Double.parseDouble(value);
                if ((d == Math.floor(d)) && !Double.isInfinite(d)) {
                    return d.intValue();
                } else {
                    if ((d.isInfinite() || d.isNaN()) && !in.isLenient()) {
                        throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + "; at path " + in.getPreviousPath());
                    } else {
                        return d;
                    }
                }
            } catch (final NumberFormatException numberFormatException) {
                throw new JsonParseException("Cannot parse " + value + "; at path " + in.getPreviousPath(), numberFormatException);
            }
        }
    }

}
