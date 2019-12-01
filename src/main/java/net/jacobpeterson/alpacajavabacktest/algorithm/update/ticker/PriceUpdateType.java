package net.jacobpeterson.alpacajavabacktest.algorithm.update.ticker;

import net.jacobpeterson.alpacajavabacktest.algorithm.update.TickerUpdateType;

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
