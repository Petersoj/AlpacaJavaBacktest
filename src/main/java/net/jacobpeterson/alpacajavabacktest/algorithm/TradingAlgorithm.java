package net.jacobpeterson.alpacajavabacktest.algorithm;

import io.github.mainstringargs.alpaca.websocket.message.AccountUpdateMessage;
import io.github.mainstringargs.alpaca.websocket.message.OrderUpdateMessage;
import io.github.mainstringargs.polygon.domain.aggregate.Result;
import net.jacobpeterson.alpacajavabacktest.AlpacaJavaBacktest;
import net.jacobpeterson.alpacajavabacktest.algorithm.update.AggregateUpdateType;
import net.jacobpeterson.alpacajavabacktest.algorithm.update.MarketEventUpdateType;
import net.jacobpeterson.alpacajavabacktest.algorithm.update.TimeUpdateType;
import net.jacobpeterson.alpacajavabacktest.broker.BacktestBroker;
import net.jacobpeterson.alpacajavabacktest.broker.portfolio.BacktestPortfolio;
import net.jacobpeterson.alpacajavabacktest.data.BacktestData;

import java.time.LocalDateTime;

/**
 * This is where your algorithm will listen to quotes, trades, aggregated data, and order updates and then promptly
 * submit orders to {@link BacktestBroker} accordingly. Simply create a new class that extends this one and use {@link
 * AlpacaJavaBacktest#run(TradingAlgorithm, LocalDateTime, LocalDateTime)}* to execute a backtest. {@link
 * BacktestPortfolio}* is updated constantly so if your algorithm references current buying power, equity, P&L, etc.,
 * you will have accurate data.
 */
public abstract class TradingAlgorithm {

    protected BacktestBroker backtestBroker;
    protected BacktestData backtestData;

    /**
     * Instantiates a new Algorithm trader.
     *
     * @param backtestBroker the backtest broker
     * @param backtestData   the backtest data
     */
    public TradingAlgorithm(BacktestBroker backtestBroker, BacktestData backtestData) {
        this.backtestBroker = backtestBroker;
        this.backtestData = backtestData;
    }

    /**
     * This method is executed before a backtest is executed. Use it to initialize your algorithm (e.g. fetch historical
     * data, asset list, etc. that you might need before the backtest is run).
     */
    public abstract void init();

    /**
     * This method is executed on the close of an aggregate time frame.
     *
     * @param aggregateUpdateType the aggregate update type
     * @param result              the aggregate result
     */
    public void onAggregateUpdate(AggregateUpdateType aggregateUpdateType, Result result) {}

    /**
     * This method is executed when a trade is executed on an exchange. For trade condition mappings see:
     * https://polygon.io/docs/#!/Stocks--Equities/get_v1_meta_conditions_ticktype
     *
     * @param ticker the ticker
     * @param trade  the trade
     */
    public void onTradeUpdate(String ticker, io.github.mainstringargs.polygon.domain.historic.trades.Tick trade) {}

    /**
     * This method is executed when there is a Level I (NBBO) quote update which occurs when there is a new best
     * bid/offer on an exchange.
     *
     * @param ticker the ticker
     * @param quote  the quote
     */
    public void onQuoteUpdate(String ticker, io.github.mainstringargs.polygon.domain.historic.quotes.Tick quote) {}

    /**
     * This method is executed when there is some sort of update to an order. For a list of possible updates, see {@link
     * io.github.mainstringargs.alpaca.enums.OrderEvent}.
     *
     * @param orderUpdateMessage the order update message
     */
    public void onOrderUpdate(OrderUpdateMessage orderUpdateMessage) {}

    /**
     * This method is execute when there is an update to the broker account. This will trigger if you deposit money into
     * the brokerage account, among other things.
     *
     * @param accountUpdateMessage the account update message
     */
    public void onAccountUpdate(AccountUpdateMessage accountUpdateMessage) {}

    /**
     * This method is executed when there is a time update.
     *
     * @param timeUpdateType the time update type
     */
    public void onTimeUpdate(TimeUpdateType timeUpdateType) {}

    /**
     * This method is executed when there is a market event update. (e.g. market open or close)
     *
     * @param marketEventUpdateType the market event update type
     */
    public void onMarketEventUpdate(MarketEventUpdateType marketEventUpdateType) {}

}
