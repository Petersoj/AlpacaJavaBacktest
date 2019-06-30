package net.jacobpeterson.trading.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;

/**
 * Used to either iterate through
 *
 * @param <T> either <Quote> or <Trade>
 */
public abstract class TickerDataIterator<T> implements Iterator<T> {

    private static final Logger LOGGER = LogManager.getLogger(TickerDataIterator.class);

    private Gson gson;
    private JsonReader jsonReader;
    private Type jsonTickerDataType;

    /**
     * Instantiates a new Ticker data iterator.
     *
     * @param gson               a gson instance
     * @param jsonReader         the json reader for the type <T> ticker data
     * @param jsonTickerDataType the json ticker data type (the incoherent <T> type)
     */
    public TickerDataIterator(Gson gson, JsonReader jsonReader, Type jsonTickerDataType) {
        this.gson = gson;
        this.jsonReader = jsonReader;
        this.jsonTickerDataType = jsonTickerDataType;
    }

    /**
     * This method seeks to translate JSON POJOs generated from vaguely worded key/values (e.g. how "bX" is "Bid Exchange")
     * (e.g. Tick.class) and convert it into an easier to use object with better method signatures/names (specifically
     * Quote.class & Trade.class). (GSON can do this internally, but the manual nature of this conversion requires the
     * confirmation of data consistency (e.g. if API JSON keywords change, GSON may not properly reflect that)).
     *
     * @return the object containing the translated data to the better, more coherent POJO
     */
    protected abstract T translateToCoherentPOJO(JsonObject tickerJsonObject);

    @Override
    public boolean hasNext() {
        try {
            return jsonReader.hasNext();
        } catch (IOException e) {
            LOGGER.error("JsonReader hasNext() error", e);
        }
        return false;
    }

    @Override
    public T next() {
        return translateToCoherentPOJO(gson.fromJson(jsonReader, jsonTickerDataType));
    }
}
