package net.jacobpeterson.alpacajavabacktest.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

/**
 * The type Gson util.
 */
public class GsonUtil {

    /** The constant GSON. */
    public static final Gson GSON = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .setLenient()
            .create();

    /** The constant JSON_PARSER. */
    public static final JsonParser JSON_PARSER = new JsonParser();

}
