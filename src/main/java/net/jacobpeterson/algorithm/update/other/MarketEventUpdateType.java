package net.jacobpeterson.algorithm.update.other;

import net.jacobpeterson.algorithm.update.OtherUpdateType;

/**
 * The enum Market event update type.
 */
public enum MarketEventUpdateType implements OtherUpdateType {

    /**
     * Market open market event update type.
     */
    MARKET_OPEN,

    /**
     * Market close market event update type.
     */
    MARKET_CLOSE,

    /**
     * Market extended open market event update type.
     */
    MARKET_PRE_OPEN,

    /**
     * Market extended close market event update type.
     */
    MARKET_PRE_CLOSE,

    /**
     * Market after open market event update type.
     */
    MARKET_AFTER_OPEN,

    /**
     * Market after close market event update type.
     */
    MARKET_AFTER_CLOSE
}
