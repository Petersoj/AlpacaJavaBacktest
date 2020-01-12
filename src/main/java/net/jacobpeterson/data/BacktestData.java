package net.jacobpeterson.data;

import io.github.mainstringargs.alpaca.AlpacaAPI;
import io.github.mainstringargs.domain.alpaca.calendar.Calendar;
import io.github.mainstringargs.domain.polygon.aggregates.Aggregate;
import io.github.mainstringargs.domain.polygon.historicquotes.HistoricQuote;
import io.github.mainstringargs.domain.polygon.historictrades.HistoricTrade;
import io.github.mainstringargs.polygon.PolygonAPI;
import net.jacobpeterson.algorithm.update.ticker.AggregateUpdateType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDate;

/**
 * Used for querying and caching data from <a href="https://polygon.io/">Polygon</a> and
 * <a href="https://alpaca.markets">Alpaca</a>.
 * <p>
 * This is completely thread safe!
 */
public class BacktestData {

    public static final String AGGREGATES_FILE_EXTENSION = "aggregates.json";
    public static final String TRADES_FILE_EXTENSION = "trades.json";
    public static final String QUOTES_FILE_EXTENSION = "quotes.json";
    public static final String BACKTEST_DATA_DIRECTORY_NAME = ".alpacajavabacktest";
    public static final String DATA_CACHE_DIRECTORY_NAME = "data_cache";

    private static final Logger LOGGER = LogManager.getLogger();

    private final AlpacaAPI alpacaAPI;
    private final PolygonAPI polygonAPI;
    private final File backtestDataDirectory;
    private final File dataCacheDirectory;

    /**
     * Instantiates a new Backtest data with <code>System.getProperty("user.home")</code> as the data directory.
     *
     * @param alpacaAPI  the alpaca api
     * @param polygonAPI the polygon api
     */
    public BacktestData(AlpacaAPI alpacaAPI, PolygonAPI polygonAPI) {
        this(alpacaAPI, polygonAPI, new File(System.getProperty("user.home"), BACKTEST_DATA_DIRECTORY_NAME));
    }

    /**
     * Instantiates a new Backtest data.
     *
     * @param alpacaAPI             the alpaca api
     * @param polygonAPI            the polygon api
     * @param backtestDataDirectory the backtest data directory (null to use a temporary directory)
     */
    public BacktestData(AlpacaAPI alpacaAPI, PolygonAPI polygonAPI, File backtestDataDirectory) {
        this.alpacaAPI = alpacaAPI;
        this.polygonAPI = polygonAPI;
        this.backtestDataDirectory = backtestDataDirectory == null ?
                                     new File(System.getProperty("java.io.tmpdir"), BACKTEST_DATA_DIRECTORY_NAME) :
                                     backtestDataDirectory;
        this.dataCacheDirectory = new File(backtestDataDirectory, DATA_CACHE_DIRECTORY_NAME);
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
        return null;
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
        return null;
    }

    /**
     * Gets data file with the following format: cache_directory/ticker_name/YYYY-MM-DD.AggregateUpdateType.extension
     *
     * @param ticker              the ticker
     * @param date                the date
     * @param aggregateUpdateType the aggregate update type (null to not include)
     * @param extension           the extension
     *
     * @return the data file
     */
    public File getDataFile(String ticker, LocalDate date, AggregateUpdateType aggregateUpdateType, String extension) {
        return new File(dataCacheDirectory, ticker + File.separator + date.toString() + "." +
                                            (aggregateUpdateType == null ? "" :
                                             aggregateUpdateType.name().toLowerCase() + ".") + extension);
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
     * Gets backtest data directory.
     *
     * @return the backtest data directory
     */
    public File getBacktestDataDirectory() {
        return backtestDataDirectory;
    }

    /**
     * Gets data cache directory.
     *
     * @return the data cache directory
     */
    public File getDataCacheDirectory() {
        return dataCacheDirectory;
    }
}
