package net.jacobpeterson.alpacajavabacktest.data.iterators;

import io.github.mainstringargs.domain.polygon.aggregates.Aggregate;
import io.github.mainstringargs.polygon.enums.Timespan;
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

    private static final Logger LOGGER = LogManager.getLogger();

    private final BacktestData backtestData;
    private final String ticker;
    private final ArrayList<LocalDate> date;
    private final ArrayList<LocalDate> datesToBeFetched;
    private final HashMap<LocalDate, File> datesCachedFiles;
    private LocalDate currentDay;

    /**
     * Instantiates a new Aggregate iterator.
     *
     * @param backtestData the backtest data
     * @param ticker       the ticker
     * @param timespan     the timespan
     * @param from         the from
     * @param to           the to
     */
    public AggregateIterator(BacktestData backtestData, String ticker, Timespan timespan, LocalDate from,
            LocalDate to) {
        this.backtestData = backtestData;
        this.ticker = ticker;
        this.date = TimeUtil.getDays(from, to);
        this.datesToBeFetched = new ArrayList<>();
        this.datesCachedFiles = new HashMap<>();
        this.currentDay = from.plusDays(0); // Make a copy

        this.populateDatesLists(timespan);
    }

    /**
     * Populate dates lists.
     *
     * @param timespan the timespan
     */
    private void populateDatesLists(Timespan timespan) {
        if (!backtestData.isPersistentCacheEnabled()) {
            datesToBeFetched.addAll(date);
        } else {
            for (LocalDate dayDate : date) {
                File dayCachedFile = backtestData.getDataFile(ticker, dayDate, timespan,
                        BacktestData.TICKER_AGGREGATES_FILE_EXTENSION);

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
        return date.get(date.size() - 1).equals(currentDay);
    }

    @Override
    public Aggregate next() {
        return null;
    }
}
