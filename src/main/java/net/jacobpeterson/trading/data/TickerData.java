package net.jacobpeterson.trading.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.mainstringargs.polygon.PolygonAPI;
import io.github.mainstringargs.polygon.domain.Quote;
import io.github.mainstringargs.polygon.domain.Trade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Used for querying and caching ticker Quotes and Trades queried from <a href="https://polygon.io/">Polygon</a>.
 */
public class TickerData {

    private static final Logger LOGGER = LogManager.getLogger(TickerData.class);

    private static final String TICKER_TRADES_FILE_EXT = ".trades.json";
    private static final String TICKER_QUOTES_FILE_EXT = ".quotes.json";

    private static final Gson GSON = new GsonBuilder().create();

    private final PolygonAPI polygonAPI;
    private final File tickerCacheDirectory;
    private final boolean persistentCacheDisabled;

    /**
     * Instantiates a new Ticker data with "user.home" as the caching directory.
     *
     * @param polygonAPI the polygon api
     */
    public TickerData(PolygonAPI polygonAPI) {
        this.polygonAPI = polygonAPI;
        this.tickerCacheDirectory = new File(System.getProperty("user.home"));
        this.persistentCacheDisabled = false;
    }

    /**
     * Instantiates a new Ticker data.
     *
     * @param polygonAPI              the polygon api
     * @param tickerCacheDirectory    the ticker cache directory
     * @param persistentCacheDisabled is persistent cache disabled
     */
    public TickerData(PolygonAPI polygonAPI, File tickerCacheDirectory, boolean persistentCacheDisabled) {
        this.polygonAPI = polygonAPI;
        this.tickerCacheDirectory = tickerCacheDirectory;
        this.persistentCacheDisabled = persistentCacheDisabled;
    }

    /**
     * Provides an iterator for Trade data on a ticker. This will fetch data from Polygon if it doesn't exist on the cache.
     * Note that Trade data does not exist from Polygon for dates earlier than Jan. 2, 2011
     *
     * @param from the from LocalDateTime
     * @param to   the to LocalDateTime
     * @return ticker trade iterator
     */
    public TickerDataIterator<Trade> getTickerTrades(LocalDateTime from, LocalDateTime to) {
        // TODO
        return null;
    }

    /**
     * Provides an iterator for Quote data on a ticker. This will fetch data from Polygon if it doesn't exist on the cache.
     * Note that Quote data does not exist from Polygon for dates earlier than Jan. 2, 2011
     *
     * @param from the from LocalDateTime
     * @param to   the to LocalDateTime
     * @return ticker quotes iterator
     */
    public TickerDataIterator<Quote> getTickerQuotes(LocalDateTime from, LocalDateTime to) {
        // TODO
        return null;
    }

    /**
     * Gets ticker cache directory.
     *
     * @return the ticker cache directory
     */
    public File getTickerCacheDirectory() {
        return tickerCacheDirectory;
    }
}
