package net.jacobpeterson.trading.algorithm;

import io.github.mainstringargs.polygon.domain.aggregate.Result;
import net.jacobpeterson.trading.backtest.BacktestBroker;
import net.jacobpeterson.trading.backtest.BacktestPortfolio;

/**
 * This is where your algorithm will listen to quotes and trades and then
 */
public abstract class AlgorithmTrader {

    protected BacktestPortfolio backtestPortfolio;
    protected BacktestBroker backtestBroker;
    protected TickerUpdateListeners[] tickerUpdateListeners;

    /**
     * Instantiates a new Algorithm trader.
     *
     * @param backtestPortfolio     the backtest portfolio
     * @param backtestBroker        the backtest broker
     * @param tickerUpdateListeners the ticker update listeners
     */
    public AlgorithmTrader(BacktestPortfolio backtestPortfolio, BacktestBroker backtestBroker,
                           TickerUpdateListeners... tickerUpdateListeners) {
        this.backtestPortfolio = backtestPortfolio;
        this.backtestBroker = backtestBroker;
        this.tickerUpdateListeners = tickerUpdateListeners;
    }

    /**
     * This method is executed on the close of an aggregate time frame.
     * The aggregate time frame may specified in the {@link #tickerUpdateListeners}
     *
     * @param result the aggregate result
     */
    public abstract void onAggregateUpdate(Result result);

    /**
     * This method is executed when a trade is executed on an exchange.
     * For trade condition mappings see: https://polygon.io/docs/#!/Stocks--Equities/get_v1_meta_conditions_ticktype
     *
     * @param trade the trade
     */
    public abstract void onTradeUpdate(io.github.mainstringargs.polygon.domain.historic.trades.Tick trade);

    /**
     * This method is executed when there is a Level I quote update which occurs when there is a new order put on an
     * exchange.
     *
     * @param quote the quote
     */
    public abstract void onQuoteUpdate(io.github.mainstringargs.polygon.domain.historic.quotes.Tick quote);

}
