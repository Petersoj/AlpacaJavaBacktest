package net.jacobpeterson.alpacajavabacktest.algorithm.update.ticker;

import net.jacobpeterson.alpacajavabacktest.algorithm.update.TickerUpdateType;

/**
 * The type Aggregate update type.
 */
public enum AggregateUpdateType implements TickerUpdateType {

    /**
     * Minute aggregate update type.
     */
    MINUTE,

    /**
     * Hour aggregate update type.
     */
    HOUR,

    /**
     * Day aggregate update type.
     */
    DAY,

    /**
     * Week aggregate update type.
     */
    WEEK,

    /**
     * Month aggregate update type.
     */
    MONTH,

    /**
     * Quarter aggregate update type.
     */
    QUARTER,

    /**
     * Year aggregate update type.
     */
    YEAR
}
