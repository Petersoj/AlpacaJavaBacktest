package net.jacobpeterson.algorithm.update.ticker;

import net.jacobpeterson.algorithm.update.TickerUpdateType;

/**
 * The type Aggregate update type.
 */
public enum AggregateUpdateType implements TickerUpdateType {

    /**
     * Minute aggregate update type.
     */
    MINUTE(1),

    /**
     * Hour aggregate update type.
     */
    HOUR(2),

    /**
     * Day aggregate update type.
     */
    DAY(3),

    /**
     * Week aggregate update type.
     */
    WEEK(4),

    /**
     * Month aggregate update type.
     */
    MONTH(5),

    /**
     * Quarter aggregate update type.
     */
    QUARTER(6),

    /**
     * Year aggregate update type.
     */
    YEAR(7);

    /**
     * The Size ranking.
     */
    private final int sizeRanking;

    AggregateUpdateType(int sizeRanking) {
        this.sizeRanking = sizeRanking;
    }

    /**
     * Gets size ranking relative to other aggregate update types.
     *
     * @return the size ranking
     */
    public int getSizeRanking() {
        return sizeRanking;
    }
}
