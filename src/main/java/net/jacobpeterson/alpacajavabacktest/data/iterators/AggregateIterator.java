package net.jacobpeterson.alpacajavabacktest.data.iterators;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.github.mainstringargs.domain.polygon.aggregates.Aggregate;
import net.jacobpeterson.alpacajavabacktest.algorithm.update.ticker.AggregateUpdateType;
import net.jacobpeterson.alpacajavabacktest.data.BacktestData;
import net.jacobpeterson.alpacajavabacktest.util.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The type Aggregate iterator.
 */
public class AggregateIterator implements Iterator<Aggregate> {

    private static final int MAX_AGGREGATE_RESULT_COUNT = 50_000;
    private static final Logger LOGGER = LogManager.getLogger();

    private final BacktestData backtestData;
    private final String ticker;
    private final AggregateUpdateType aggregateUpdateType;
    private final ArrayList<LocalDate> dates;
    private final HashMap<LocalDate, Aggregate> datesFetchedCache;
    private final HashMap<LocalDate, File> datesFilesCached;
    private int currentDateIndex;
    private JsonReader currentFileReader;
    private JsonWriter currentFileWriter;

    /**
     * Instantiates a new Aggregate iterator.
     *
     * @param backtestData        the backtest data
     * @param ticker              the ticker
     * @param aggregateUpdateType the aggregate update type
     * @param from                the from
     * @param to                  the to
     */
    public AggregateIterator(BacktestData backtestData, String ticker, AggregateUpdateType aggregateUpdateType,
            LocalDate from, LocalDate to) {
        this.backtestData = backtestData;
        this.ticker = ticker;
        this.aggregateUpdateType = aggregateUpdateType;
        this.dates = TimeUtil.getAggregateDateIntervals(aggregateUpdateType, from, to);
        this.datesFetchedCache = new HashMap<>();
        this.datesFilesCached = new HashMap<>();
        this.currentDateIndex = 0;

        this.populateDatesLists();
    }

    /**
     * Populate dates lists.
     */
    private void populateDatesLists() {
        for (LocalDate dayDate : dates) {
            File dayCachedFile = backtestData.getDataFile(ticker, dayDate, aggregateUpdateType,
                    BacktestData.AGGREGATES_FILE_EXTENSION);

            if (dayCachedFile.exists()) {
                datesFilesCached.put(dayDate, dayCachedFile);
            } else {
                datesFetchedCache.put(dayDate, null);
            }
        }
    }

    @Override
    public boolean hasNext() {
        return currentDateIndex - 1 != dates.size();
    }

    @Override
    public Aggregate next() {
        return null;
    }

    /**
     * Gets ticker.
     *
     * @return the ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * Gets aggregate update type.
     *
     * @return the aggregate update type
     */
    public AggregateUpdateType getAggregateUpdateType() {
        return aggregateUpdateType;
    }
}
