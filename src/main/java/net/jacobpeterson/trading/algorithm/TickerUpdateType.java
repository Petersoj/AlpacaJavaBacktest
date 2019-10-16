package net.jacobpeterson.trading.algorithm;

/**
 * These enums are used to specify which updates/methods will be triggered/executed in the
 * {@link TradingAlgorithm}.
 */
public enum TickerUpdateType {

    /**
     * Quotes ticker update type.
     */
    QUOTES,

    /**
     * Trades ticker update type.
     */
    TRADES,

    /**
     * Minute aggregates ticker update type.
     */
    MINUTE_AGGREGATES,

    /**
     * Hour aggregates ticker update type.
     */
    HOUR_AGGREGATES,

    /**
     * Day aggregates ticker update type.
     */
    DAY_AGGREGATES,

    /**
     * Week aggregates ticker update type.
     */
    WEEK_AGGREGATES,

    /**
     * Month aggregates ticker update type.
     */
    MONTH_AGGREGATES,

    /**
     * Quarter aggregates ticker update type.
     */
    QUARTER_AGGREGATES,

    /**
     * Year aggregates ticker update type.
     */
    YEAR_AGGREGATES
}
