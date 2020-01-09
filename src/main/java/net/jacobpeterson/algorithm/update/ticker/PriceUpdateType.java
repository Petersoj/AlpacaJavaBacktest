package net.jacobpeterson.algorithm.update.ticker;

import net.jacobpeterson.algorithm.update.TickerUpdateType;

/**
 * The enum Price update type.
 */
public enum PriceUpdateType implements TickerUpdateType {

    /**
     * Trade price update type.
     */
    TRADE,

    /**
     * Quote price update type.
     */
    QUOTE,
}
