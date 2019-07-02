package net.jacobpeterson.trading.backtest;

import io.github.mainstringargs.alpaca.domain.Order;

/**
 * This is used as a simulated broker to submit orders on a stock exchange.
 * These orders are compared against bid/ask prices as well as traded prices of an equity. This ensures that the
 * backtesting is most accurate.
 */
public class BacktestBroker {

    public Order submitOrder() {
        return null;
    }

    public void cancelOrder() {

    }

}
