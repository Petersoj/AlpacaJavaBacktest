package net.jacobpeterson.trading.algorithm;

/**
 * These enums are used to specify which updates will be triggered in the
 * {@link net.jacobpeterson.trading.algorithm.AlgorithmTrader}.
 */
public enum TickerUpdateListeners {

    QUOTES,
    TRADES,
    MINUTE_AGGREGATES,
    HOUR_AGGREGATES,
    DAY_AGGREGATES,
    WEEK_AGGREGATES,
    MONTH_AGGREGATES,
    QUARTER_AGGREGATES,
    YEAR_AGGREGATES

}
