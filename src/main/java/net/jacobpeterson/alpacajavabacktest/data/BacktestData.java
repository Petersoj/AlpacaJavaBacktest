package net.jacobpeterson.alpacajavabacktest.data;

import io.github.mainstringargs.alpaca.AlpacaAPI;
import io.github.mainstringargs.domain.alpaca.calendar.Calendar;
import io.github.mainstringargs.domain.polygon.aggregates.Aggregate;
import io.github.mainstringargs.domain.polygon.historicquotes.HistoricQuote;
import io.github.mainstringargs.domain.polygon.historictrades.HistoricTrade;
import io.github.mainstringargs.polygon.PolygonAPI;
import io.github.mainstringargs.polygon.enums.Timespan;
import net.jacobpeterson.alpacajavabacktest.data.iterators.AggregateIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDate;
import java.util.Iterator;

/**
 * Used for querying and caching data from <a href="https://polygon.io/">Polygon</a> and
 * <a href="https://alpaca.markets">Alpaca</a>.
 */
public class BacktestData {

    public static final String TICKER_AGGREGATES_FILE_EXTENSION = "aggregates.json";
    public static final String TICKER_TRADES_FILE_EXTENSION = "trades.json";
    public static final String TICKER_QUOTES_FILE_EXTENSION = "quotes.json";
    public static final String BACKTEST_FILES_DIRECTORY_NAME = ".alpacajavabacktest";
    public static final String DATA_DIRECTORY_NAME = "data";

    private static final Logger LOGGER = LogManager.getLogger();

    private final AlpacaAPI alpacaAPI;
    private final PolygonAPI polygonAPI;
    private File tickerCacheDirectory;
    private boolean persistentCacheEnabled;

    /**
     * Instantiates a new Ticker data with <code>System.getProperty("user.home")</code> as the caching directory.
     *
     * @param alpacaAPI  the alpaca api
     * @param polygonAPI the polygon api
     */
    public BacktestData(AlpacaAPI alpacaAPI, PolygonAPI polygonAPI) {
        this(alpacaAPI, polygonAPI, new File(System.getProperty("user.home"),
                BACKTEST_FILES_DIRECTORY_NAME + "/" + DATA_DIRECTORY_NAME), true);
    }

    /**
     * Instantiates a new Ticker data.
     *
     * @param alpacaAPI              the alpaca api
     * @param polygonAPI             the polygon api
     * @param tickerCacheDirectory   the ticker cache directory
     * @param persistentCacheEnabled the persistent cache enabled
     */
    public BacktestData(AlpacaAPI alpacaAPI, PolygonAPI polygonAPI, File tickerCacheDirectory,
            boolean persistentCacheEnabled) {
        this.alpacaAPI = alpacaAPI;
        this.polygonAPI = polygonAPI;
        this.tickerCacheDirectory = tickerCacheDirectory;
        this.persistentCacheEnabled = persistentCacheEnabled;
    }

    /**
     * Provides an iterable for Aggregate data on a ticker. This will fetch data from Polygon if it doesn't exist on the
     * cache (or if the cache is disabled).
     *
     * @param ticker   the ticker
     * @param timespan the timespan
     * @param from     the from LocalDate (inclusive)
     * @param to       the to LocalDate (exclusive)
     *
     * @return the aggregates
     */
    public Iterable<Aggregate> getAggregates(String ticker, Timespan timespan, LocalDate from, LocalDate to) {
        synchronized (BacktestData.class) {
            return () -> new AggregateIterator(this, ticker, timespan, from, to);
        }
    }

    /**
     * Provides an iterator for Trade data on a ticker. This will fetch data from Polygon if it doesn't exist on the
     * cache (or if the cache is disabled).
     *
     * @param ticker the ticker
     * @param from   the from LocalDate
     * @param to     the to LocalDate
     *
     * @return ticker trade iterator
     */
    public Iterator<HistoricTrade> getTickerTrades(String ticker, LocalDate from, LocalDate to) {
        synchronized (BacktestData.class) {

        }
        return null;
    }

    /**
     * Provides an iterator for Quote data on a ticker. This will fetch data from Polygon if it doesn't exist on the
     * cache (or if the cache is disabled).
     *
     * @param ticker the ticker
     * @param from   the from LocalDate
     * @param to     the to LocalDate
     *
     * @return ticker quotes iterator
     */
    public Iterator<HistoricQuote> getTickerQuotes(String ticker, LocalDate from, LocalDate to) {
        synchronized (BacktestData.class) {

        }
        return null;
    }

    /**
     * Provides an iterator for the stock market calendar. This will fetch data from Polygon if it doesn't exist on the
     * cache (or if the cache is disabled).
     *
     * @param from the from
     * @param to   the to
     *
     * @return the calendar
     */
    public Iterator<Calendar> getCalendar(LocalDate from, LocalDate to) {
        synchronized (BacktestData.class) {

        }
        return null;
    }

    /**
     * Gets data file with the following format: cached_directory/ticker_name/YYYY-MM-DD.timespan.extension
     *
     * @param ticker    the ticker
     * @param date      the date
     * @param timespan  the timespan (null to not include)
     * @param extension the extension
     *
     * @return the data file
     */
    public File getDataFile(String ticker, LocalDate date, Timespan timespan, String extension) {
        return new File(tickerCacheDirectory, ticker + "/" + date.toString() + "." +
                (timespan == null ? "" : timespan.getAPIName() + ".") + extension);
    }

    /**
     * Gets alpaca api.
     *
     * @return the alpaca api
     */
    public AlpacaAPI getAlpacaAPI() {
        return alpacaAPI;
    }

    /**
     * Gets polygon api.
     *
     * @return the polygon api
     */
    public PolygonAPI getPolygonAPI() {
        return polygonAPI;
    }

    /**
     * Gets ticker cache directory.
     *
     * @return the ticker cache directory
     */
    public File getTickerCacheDirectory() {
        return tickerCacheDirectory;
    }

    /**
     * Sets ticker cache directory.
     *
     * @param tickerCacheDirectory the ticker cache directory
     */
    public void setTickerCacheDirectory(File tickerCacheDirectory) {
        this.tickerCacheDirectory = tickerCacheDirectory;
    }

    /**
     * Is persistent cache enabled boolean.
     *
     * @return the boolean
     */
    public boolean isPersistentCacheEnabled() {
        return persistentCacheEnabled;
    }

    /**
     * Sets persistent cache enabled.
     *
     * @param persistentCacheEnabled the persistent cache enabled
     */
    public void setPersistentCacheEnabled(boolean persistentCacheEnabled) {
        this.persistentCacheEnabled = persistentCacheEnabled;
    }
}
