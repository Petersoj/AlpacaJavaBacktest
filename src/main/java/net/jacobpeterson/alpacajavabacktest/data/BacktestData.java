package net.jacobpeterson.alpacajavabacktest.data;

import io.github.mainstringargs.alpaca.AlpacaAPI;
import io.github.mainstringargs.domain.alpaca.calendar.Calendar;
import io.github.mainstringargs.domain.polygon.aggregates.Aggregate;
import io.github.mainstringargs.domain.polygon.historicquotes.HistoricQuote;
import io.github.mainstringargs.domain.polygon.historictrades.HistoricTrade;
import io.github.mainstringargs.polygon.PolygonAPI;
import net.jacobpeterson.alpacajavabacktest.algorithm.update.ticker.AggregateUpdateType;
import net.jacobpeterson.alpacajavabacktest.data.iterators.AggregateIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDate;

/**
 * Used for querying and caching data from <a href="https://polygon.io/">Polygon</a> and
 * <a href="https://alpaca.markets">Alpaca</a>.
 */
public class BacktestData {

    public static final String AGGREGATES_FILE_EXTENSION = "aggregates.json";
    public static final String TRADES_FILE_EXTENSION = "trades.json";
    public static final String QUOTES_FILE_EXTENSION = "quotes.json";
    public static final String BACKTEST_FILES_DIRECTORY_NAME = ".alpacajavabacktest";
    public static final String DATA_DIRECTORY_NAME = "data";

    private static final Logger LOGGER = LogManager.getLogger();

    private final AlpacaAPI alpacaAPI;
    private final PolygonAPI polygonAPI;
    private File dataCacheDirectory;
    private boolean persistentCacheEnabled;

    /**
     * Instantiates a new Backtest data with <code>System.getProperty("user.home")</code> as the caching directory.
     *
     * @param alpacaAPI  the alpaca api
     * @param polygonAPI the polygon api
     */
    public BacktestData(AlpacaAPI alpacaAPI, PolygonAPI polygonAPI) {
        this(alpacaAPI, polygonAPI, new File(System.getProperty("user.home"),
                BACKTEST_FILES_DIRECTORY_NAME + "/" + DATA_DIRECTORY_NAME), true);
    }

    /**
     * Instantiates a new Backtest data.
     *
     * @param alpacaAPI              the alpaca api
     * @param polygonAPI             the polygon api
     * @param dataCacheDirectory     the data cache directory
     * @param persistentCacheEnabled the persistent cache enabled
     */
    public BacktestData(AlpacaAPI alpacaAPI, PolygonAPI polygonAPI, File dataCacheDirectory,
            boolean persistentCacheEnabled) {
        this.alpacaAPI = alpacaAPI;
        this.polygonAPI = polygonAPI;
        this.dataCacheDirectory = dataCacheDirectory;
        this.persistentCacheEnabled = persistentCacheEnabled;
    }

    /**
     * Provides an iterable for Aggregate data on a ticker. This will fetch data from Polygon if it doesn't exist on the
     * cache (or if the cache is disabled).
     *
     * @param ticker              the ticker
     * @param aggregateUpdateType the aggregate update type
     * @param from                the from LocalDate (inclusive)
     * @param to                  the to LocalDate (exclusive)
     *
     * @return the aggregates
     */
    public Iterable<Aggregate> getAggregates(String ticker, AggregateUpdateType aggregateUpdateType, LocalDate from,
            LocalDate to) {
        synchronized (BacktestData.class) {
            return () -> new AggregateIterator(this, ticker, aggregateUpdateType, from, to);
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
     * @return the trades
     */
    public Iterable<HistoricTrade> getTrades(String ticker, LocalDate from, LocalDate to) {
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
     * @return the quotes
     */
    public Iterable<HistoricQuote> getQuotes(String ticker, LocalDate from, LocalDate to) {
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
    public Iterable<Calendar> getCalendar(LocalDate from, LocalDate to) {
        synchronized (BacktestData.class) {

        }
        return null;
    }

    /**
     * Gets data file with the following format: cached_directory/ticker_name/YYYY-MM-DD.AggregateUpdateType.extension
     *
     * @param ticker              the ticker
     * @param date                the date
     * @param aggregateUpdateType the aggregate update type (null to not include)
     * @param extension           the extension
     *
     * @return the data file
     */
    public File getDataFile(String ticker, LocalDate date, AggregateUpdateType aggregateUpdateType, String extension) {
        return new File(dataCacheDirectory, ticker + "/" + date.toString() + "." +
                (aggregateUpdateType == null ? "" : aggregateUpdateType.name().toLowerCase() + ".") + extension);
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
     * Gets data cache directory.
     *
     * @return the data cache directory
     */
    public File getDataCacheDirectory() {
        return dataCacheDirectory;
    }

    /**
     * Sets data cache directory.
     *
     * @param dataCacheDirectory the data cache directory
     */
    public void setDataCacheDirectory(File dataCacheDirectory) {
        this.dataCacheDirectory = dataCacheDirectory;
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
