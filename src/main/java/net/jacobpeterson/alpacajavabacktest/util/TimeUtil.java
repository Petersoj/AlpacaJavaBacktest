package net.jacobpeterson.alpacajavabacktest.util;

import java.time.LocalDate;
import java.util.ArrayList;

public class TimeUtil {

    /**
     * Gets days.
     *
     * @param from the from (inclusive)
     * @param to   the to (exclusive)
     *
     * @return the iterable
     */
    public static ArrayList<LocalDate> getDays(LocalDate from, LocalDate to) {
        ArrayList<LocalDate> dayDates = new ArrayList<>();

        for (LocalDate date = from.plusDays(0); date.isBefore(to); date = date.plusDays(1)) {
            dayDates.add(date);
        }

        return dayDates;
    }
}
