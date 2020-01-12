package net.jacobpeterson.util;

import net.jacobpeterson.algorithm.update.ticker.AggregateUpdateType;

import java.time.LocalDate;
import java.util.ArrayList;

public class TimeUtil {

    /**
     * Gets date intervals.
     *
     * @param aggregateUpdateType the aggregate update type
     * @param from                the from (inclusive)
     * @param to                  the to (exclusive)
     *
     * @return the iterable
     */
    public static ArrayList<LocalDate> getAggregateDateIntervals(AggregateUpdateType aggregateUpdateType,
                                                                 LocalDate from, LocalDate to) {
        ArrayList<LocalDate> dayDates = new ArrayList<>();
        LocalDate currentDate = from.plusDays(0); // Make a copy

        while (currentDate.isBefore(to)) {
            dayDates.add(currentDate);

            switch (aggregateUpdateType) {
                case MINUTE:
                case HOUR:
                case DAY:
                    currentDate = currentDate.plusDays(1);
                    break;
                case WEEK:
                    currentDate = currentDate.plusWeeks(1);
                    break;
                case MONTH:
                    currentDate = currentDate.plusMonths(1);
                    break;
                case QUARTER:
                    currentDate = currentDate.plusMonths(3);
                    break;
                case YEAR:
                    currentDate = currentDate.plusYears(1);
                    break;
            }
        }

        return dayDates;
    }
}
