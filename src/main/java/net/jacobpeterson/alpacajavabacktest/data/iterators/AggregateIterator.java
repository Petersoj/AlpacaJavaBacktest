package net.jacobpeterson.alpacajavabacktest.data.iterators;

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
    private final ArrayList<LocalDate> datesToBeFetched;
    private final HashMap<LocalDate, File> datesCachedFiles;
    private int currentDateIndex;

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
        this.dates = TimeUtil.getDateIntervals(aggregateUpdateType, from, to);
        this.datesToBeFetched = new ArrayList<>();
        this.datesCachedFiles = new HashMap<>();
        this.currentDateIndex = 0;

        this.populateDatesLists();
    }

    /**
     * Populate dates lists.
     */
    private void populateDatesLists() {
        if (!backtestData.isPersistentCacheEnabled()) {
            datesToBeFetched.addAll(dates);
        } else {
            for (LocalDate dayDate : dates) {
                File dayCachedFile = backtestData.getDataFile(ticker, dayDate, aggregateUpdateType,
                        BacktestData.AGGREGATES_FILE_EXTENSION);

                if (dayCachedFile.exists()) {
                    datesCachedFiles.put(dayDate, dayCachedFile);
                } else {
                    datesToBeFetched.add(dayDate);
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return dates.size() == currentDateIndex - 1;
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
