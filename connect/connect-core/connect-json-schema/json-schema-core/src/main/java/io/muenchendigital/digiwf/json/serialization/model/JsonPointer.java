package io.muenchendigital.digiwf.json.serialization.model;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;

/**
 * Copy of the JSONPointer class with additional functionality
 */
@Getter
public class JsonPointer {

    // used for URL encoding and decoding
    private static final String ENCODING = "utf-8";

    // Segments for the JSONPointer string
    private final List<String> refTokens;

    /**
     * Pre-parses and initializes a new {@code JSONPointer} instance. If you want to
     * evaluate the same JSON Pointer on different JSON documents then it is recommended
     * to keep the {@code JSONPointer} instances due to performance considerations.
     *
     * @param pointer the JSON String or URI Fragment representation of the JSON pointer.
     * @throws IllegalArgumentException if {@code pointer} is not a valid JSON pointer
     */
    public JsonPointer(final String pointer) {
        if (pointer == null) {
            throw new NullPointerException("pointer cannot be null");
        }
        if (pointer.isEmpty() || pointer.equals("#")) {
            this.refTokens = Collections.emptyList();
            return;
        }
        String refs;
        if (pointer.startsWith("#/")) {
            refs = pointer.substring(2);
            try {
                refs = URLDecoder.decode(refs, ENCODING);
            } catch (final UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } else if (pointer.startsWith("/")) {
            refs = pointer.substring(1);
        } else {
            throw new IllegalArgumentException("a JSON pointer should start with '/' or '#/'");
        }
        this.refTokens = new ArrayList<String>();
        int slashIdx = -1;
        int prevSlashIdx = 0;
        do {
            prevSlashIdx = slashIdx + 1;
            slashIdx = refs.indexOf('/', prevSlashIdx);
            if (prevSlashIdx == slashIdx || prevSlashIdx == refs.length()) {
                // found 2 slashes in a row ( obj//next )
                // or single slash at the end of a string ( obj/test/ )
                this.refTokens.add("");
            } else if (slashIdx >= 0) {
                final String token = refs.substring(prevSlashIdx, slashIdx);
                this.refTokens.add(unescape(token));
            } else {
                // last item after separator, or no separator at all.
                final String token = refs.substring(prevSlashIdx);
                this.refTokens.add(unescape(token));
            }
        } while (slashIdx >= 0);
        // using split does not take into account consecutive separators or "ending nulls"
        //for (String token : refs.split("/")) {
        //    this.refTokens.add(unescape(token));
        //}
    }

    public JsonPointer(final List<String> refTokens) {
        this.refTokens = new ArrayList<String>(refTokens);
    }

    private static String unescape(final String token) {
        return token.replace("~1", "/").replace("~0", "~")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }

    /**
     * Evaluates this JSON Pointer on the given {@code document}. The {@code document}
     * is usually a {@link JSONObject} or a {@link JSONArray} instance, but the empty
     * JSON Pointer ({@code ""}) can be evaluated on any JSON values and in such case the
     * returned value will be {@code document} itself.
     *
     * @param document the JSON document which should be the subject of querying.
     * @return the result of the evaluation
     * @throws JSONPointerException if an error occurs during evaluation
     */
    public Object queryFrom(final Object document) throws JSONPointerException {
        if (this.refTokens.isEmpty()) {
            return document;
        }
        Object current = document;
        for (final String token : this.refTokens) {
            if (current instanceof JSONObject) {
                current = ((JSONObject) current).opt(unescape(token));
            } else if (current instanceof JSONArray) {
                current = readByIndexToken(current, token);
            } else {
                throw new JSONPointerException(format(
                        "value [%s] is not an array or object therefore its key %s cannot be resolved", current,
                        token));
            }
        }
        return current;
    }

    /**
     * Generates a Object structure from a JsonPointer.
     * Does not work with arrays.
     *
     * @param value Value that should be inserted
     * @return object structure
     * @throws JSONPointerException
     */
    public JSONObject generateObjectStructure(final Object value) throws JSONPointerException {
        if (this.refTokens.isEmpty()) {
            return new JSONObject();
        }

        if (this.refTokens.size() == 1) {
            return new JSONObject().put(this.refTokens.get(0), value);
        }

        final JSONObject obj = new JSONObject();
        JSONObject currentObject = obj;
        final Iterator<String> refTokenIterator = this.refTokens.iterator();
        while (refTokenIterator.hasNext()) {
            final String token = refTokenIterator.next();
            if (!refTokenIterator.hasNext()) {
                currentObject.put(token, value);
                continue;
            } else {
                final JSONObject actualObject = new JSONObject();
                currentObject.put(token, actualObject);
                currentObject = actualObject;
            }
        }
        return obj;
    }

    /**
     * Matches a JSONArray element by ordinal position
     *
     * @param current    the JSONArray to be evaluated
     * @param indexToken the array index in string form
     * @return the matched object. If no matching item is found a
     * @throws JSONPointerException is thrown if the index is out of bounds
     */
    private static Object readByIndexToken(final Object current, final String indexToken) throws JSONPointerException {
        try {
            final int index = Integer.parseInt(indexToken);
            final JSONArray currentArr = (JSONArray) current;
            if (index >= currentArr.length()) {
                throw new JSONPointerException(format("index %s is out of bounds - the array has %d elements", indexToken,
                        Integer.valueOf(currentArr.length())));
            }
            try {
                return currentArr.get(index);
            } catch (final JSONException e) {
                throw new JSONPointerException("Error reading value at index position " + index, e);
            }
        } catch (final NumberFormatException e) {
            throw new JSONPointerException(format("%s is not an array index", indexToken), e);
        }
    }

    /**
     * Returns a string representing the JSONPointer path value using string
     * representation
     */
    @Override
    public String toString() {
        final StringBuilder rval = new StringBuilder("");
        for (final String token : this.refTokens) {
            rval.append('/').append(escape(token));
        }
        return rval.toString();
    }

    /**
     * Escapes path segment values to an unambiguous form.
     * The escape char to be inserted is '~'. The chars to be escaped
     * are ~, which maps to ~0, and /, which maps to ~1. Backslashes
     * and double quote chars are also escaped.
     *
     * @param token the JSONPointer segment value to be escaped
     * @return the escaped value for the token
     */
    private static String escape(final String token) {
        return token.replace("~", "~0")
                .replace("/", "~1")
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }

    /**
     * Returns a string representing the JSONPointer path value using URI
     * fragment identifier representation
     *
     * @return a uri fragment string
     */
    public String toURIFragment() {
        try {
            final StringBuilder rval = new StringBuilder("#");
            for (final String token : this.refTokens) {
                rval.append('/').append(URLEncoder.encode(token, ENCODING));
            }
            return rval.toString();
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
