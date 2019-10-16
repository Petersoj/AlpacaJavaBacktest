package net.jacobpeterson.trading.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.mainstringargs.polygon.PolygonAPI;
import io.github.mainstringargs.polygon.domain.aggregate.Aggregates;
import io.github.mainstringargs.polygon.domain.aggregate.Result;
import io.github.mainstringargs.polygon.enums.Timespan;
import io.github.mainstringargs.polygon.rest.exceptions.PolygonAPIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

/**
 * Used for querying and caching ticker Quotes, Trades, and aggregates queried from
 * <a href="https://polygon.io/">Polygon</a>.
 */
public class TickerData {

    private static final Logger LOGGER = LogManager.getLogger(TickerData.class);
    private static final String TICKER_AGGREGATES_FILE_EXT = ".aggregates.json";
    private static final String TICKER_TRADES_FILE_EXT = ".trades.json";
    private static final String TICKER_QUOTES_FILE_EXT = ".quotes.json";
    private static final String CACHE_DIRECTORY_NAME = ".alpacajavabacktest";
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
        this.tickerCacheDirectory = new File(System.getProperty("user.home"), CACHE_DIRECTORY_NAME);
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
     * Provides an iterator for Aggregate data on a ticker. This will fetch data from Polygon if it doesn't exist on the
     * cache. The Iterator is buffered (via {@link com.google.gson.stream.JsonReader} from a data file in
     * {@link #getTickerCacheDirectory()}).
     * Note that Trade data does not exist from Polygon for dates earlier than Jan. 2, 2011
     *
     * @param ticker   the ticker
     * @param timespan the timespan
     * @param from     the from LocalDate
     * @param to       the to LocalDate
     * @return the aggregates
     */
    public Iterator<Result> getAggregates(String ticker, Timespan timespan, LocalDate from, LocalDate to)
            throws PolygonAPIException, IOException {

        Aggregates aggregates;

        if (tickerCacheDirectory.exists()) {

        }

//        LOGGER.info("{} Aggregates for {} using {} timespan from {} to {}.", loadType, ticker, timespan.name(),
//                from.toString(), to.toString());

        aggregates = polygonAPI.getAggregates(ticker, 1, timespan, from, to, false);

        return null;
    }

    /**
     * Provides an iterator for Trade data on a ticker. This will fetch data from Polygon if it doesn't exist on the cache.
     * The Iterator is buffered (via {@link com.google.gson.stream.JsonReader} from a data file in
     * {@link #getTickerCacheDirectory()}).
     * Note that Trade data does not exist from Polygon for dates earlier than Jan. 2, 2011
     *
     * @param ticker the ticker
     * @param from   the from LocalDate
     * @param to     the to LocalDate
     * @return ticker trade iterator
     */
    public Iterator<io.github.mainstringargs.polygon.domain.historic.trades.Tick> getTickerTrades(String ticker,
                                                                                                  LocalDate from,
                                                                                                  LocalDate to) {
        // TODO
        return null;
    }

    /**
     * Provides an iterator for Quote data on a ticker. This will fetch data from Polygon if it doesn't exist on the cache.
     * The Iterator is buffered (via {@link com.google.gson.stream.JsonReader} from a data file in
     * {@link #getTickerCacheDirectory()}).
     * Note that Quote data does not exist from Polygon for dates earlier than Jan. 2, 2011
     *
     * @param ticker the ticker
     * @param from   the from LocalDate
     * @param to     the to LocalDate
     * @return ticker quotes iterator
     */
    public Iterator<io.github.mainstringargs.polygon.domain.historic.quotes.Tick> getTickerQuotes(String ticker,
                                                                                                  LocalDate from,
                                                                                                  LocalDate to) {
        // TODO
        return null;
    }

    public void formatFile() {

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
