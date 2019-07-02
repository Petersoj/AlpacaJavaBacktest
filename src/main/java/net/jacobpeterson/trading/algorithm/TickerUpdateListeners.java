package net.jacobpeterson.trading.algorithm;

/**
 * These enums are used to specify which updates/methods will be triggered/executed in the
 * {@link net.jacobpeterson.trading.algorithm.AlgorithmTrader}.
 */
public enum TickerUpdateListeners {

    /**
     * Quotes ticker update listener.
     */
    QUOTES,

    /**
     * Trades ticker update listener.
     */
    TRADES,

    /**
     * Minute aggregates ticker update listener.
     */
    MINUTE_AGGREGATES,

    /**
     * Hour aggregates ticker update listener.
     */
    HOUR_AGGREGATES,

    /**
     * Day aggregates ticker update listener.
     */
    DAY_AGGREGATES,

    /**
     * Week aggregates ticker update listener.
     */
    WEEK_AGGREGATES,

    /**
     * Month aggregates ticker update listener.
     */
    MONTH_AGGREGATES,

    /**
     * Quarter aggregates ticker update listener.
     */
    QUARTER_AGGREGATES,

    /**
     * Year aggregates ticker update listener.
     */
    YEAR_AGGREGATES

}
