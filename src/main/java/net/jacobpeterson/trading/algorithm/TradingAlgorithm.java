package net.jacobpeterson.trading.algorithm;

import io.github.mainstringargs.alpaca.domain.Order;
import io.github.mainstringargs.polygon.domain.aggregate.Result;
import io.github.mainstringargs.polygon.enums.Timespan;
import net.jacobpeterson.trading.AlpacaJavaBacktest;
import net.jacobpeterson.trading.backtest.BacktestBroker;
import net.jacobpeterson.trading.backtest.BacktestPortfolio;

/**
 * This is where your algorithm will listen to quotes, trades, aggregated data, and order updates and then promptly
 * submit orders to {@link BacktestBroker} accordingly. Simply create a new class that extends this one and use {@link
 * AlpacaJavaBacktest#backtest()} to execute a backtest. {@link BacktestPortfolio} is updated constantly so if your
 * algorithm references current buying power, equity, P&L, etc., you will have accurate data.
 */
public abstract class TradingAlgorithm {

    protected BacktestPortfolio backtestPortfolio;
    protected BacktestBroker backtestBroker;

    /**
     * Instantiates a new Algorithm trader.
     *
     * @param backtestPortfolio the backtest portfolio
     * @param backtestBroker    the backtest broker
     */
    public TradingAlgorithm(BacktestPortfolio backtestPortfolio, BacktestBroker backtestBroker) {
        this.backtestPortfolio = backtestPortfolio;
        this.backtestBroker = backtestBroker;
    }

    /**
     * This method is executed on the close of an aggregate time frame. The aggregate time frame may specified in the
     * {@link TickerUpdateType}
     *
     * @param result   the aggregate result
     * @param timespan the timespan of the aggregate {@link Result}
     */
    public abstract void onAggregateUpdate(Result result, Timespan timespan);

    /**
     * This method is executed when a trade is executed on an exchange. For trade condition mappings see:
     * https://polygon.io/docs/#!/Stocks--Equities/get_v1_meta_conditions_ticktype
     *
     * @param ticker the ticker
     * @param trade  the trade
     */
    public abstract void onTradeUpdate(String ticker,
                                       io.github.mainstringargs.polygon.domain.historic.trades.Tick trade);

    /**
     * This method is executed when there is a Level I quote update which occurs when there is a new order put on an
     * exchange.
     *
     * @param ticker the ticker
     * @param quote  the quote
     */
    public abstract void onQuoteUpdate(String ticker,
                                       io.github.mainstringargs.polygon.domain.historic.quotes.Tick quote);

    /**
     * This method is executed when an order placed via the BacktestBroker is filled. Note that an order may be filled
     * completely or partially filled. Use {@link Order#getFilledQty()} to get how much of the order was actually
     * filled. It is a good idea to implement some sort of order tracking mechanism in a trading algorithm so that, in
     * the event that an order is not completely filled, your algorithm can adjust/cancel the order accordingly.
     *
     * @param order the order filled
     */
    public abstract void onOrderUpdate(Order order);

}
