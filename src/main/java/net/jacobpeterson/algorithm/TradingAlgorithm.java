package net.jacobpeterson.algorithm;

import io.github.mainstringargs.domain.alpaca.websocket.account.AccountUpdate;
import io.github.mainstringargs.domain.alpaca.websocket.trade.TradeUpdate;
import io.github.mainstringargs.domain.polygon.aggregates.Aggregate;
import io.github.mainstringargs.domain.polygon.historicquotes.HistoricQuote;
import io.github.mainstringargs.domain.polygon.historictrades.HistoricTrade;
import net.jacobpeterson.AlpacaJavaBacktest;
import net.jacobpeterson.algorithm.update.other.MarketEventUpdateType;
import net.jacobpeterson.algorithm.update.ticker.AggregateUpdateType;
import net.jacobpeterson.broker.BacktestBroker;
import net.jacobpeterson.data.BacktestData;

/**
 * This is where your algorithm will listen to quotes, trades, aggregated data, and order updates and then promptly
 * submit orders to {@link BacktestBroker} accordingly. Simply create a new class that extends this one and use {@link
 * AlpacaJavaBacktest#run(TradingAlgorithm)} to execute a backtest. {@link BacktestBroker} is updated constantly so if
 * your algorithm references current buying power, equity, P&L, etc., you will have accurate data.
 */
public abstract class TradingAlgorithm {

    protected BacktestData backtestData;
    protected BacktestBroker backtestBroker;

    /**
     * This method is executed before a backtest is executed. You'll then have access to {@link #getBacktestBroker()}
     * and {@link #getBacktestData()}.
     * <p>
     * Use it to initialize your algorithm (e.g. fetch historical data via {@link BacktestData}, asset list, etc. that
     * you might need before the backtest is run).
     */
    public abstract void init();

    /**
     * This method is executed on the close of an aggregate time frame.
     *
     * @param aggregateUpdateType the aggregate update type
     * @param aggregate           the aggregate
     */
    public void onAggregateUpdate(AggregateUpdateType aggregateUpdateType, Aggregate aggregate) {}

    /**
     * This method is executed when a trade is executed on an exchange.
     *
     * @param historicTrade the historic trade
     */
    public void onTradeUpdate(HistoricTrade historicTrade) {}

    /**
     * This method is executed when there is a Level I (NBBO) quote update which occurs when there is a new best
     * bid/offer on an exchange.
     *
     * @param quote the quote
     */
    public void onQuoteUpdate(HistoricQuote quote) {}

    /**
     * This method is executed when there is a trade update to an order.
     *
     * @param tradeUpdate the trade update
     */
    public void onTradeUpdate(TradeUpdate tradeUpdate) {}

    /**
     * This method is execute when there is an update to the backtest broker account. This will trigger if you deposit
     * money into the brokerage account, among other things.
     *
     * @param accountUpdate the account update
     */
    public void onAccountUpdate(AccountUpdate accountUpdate) {}

    /**
     * This method is executed when there is a market event update.
     *
     * @param marketEventUpdateType the market event update type
     */
    public void onMarketEventUpdate(MarketEventUpdateType marketEventUpdateType) {}

    /**
     * Gets backtest data.
     *
     * @return the backtest data
     */
    public final BacktestData getBacktestData() {
        return backtestData;
    }

    /**
     * Sets backtest data.
     *
     * @param backtestData the backtest data
     */
    public final void setBacktestData(BacktestData backtestData) {
        this.backtestData = backtestData;
    }

    /**
     * Gets backtest broker.
     *
     * @return the backtest broker
     */
    public final BacktestBroker getBacktestBroker() {
        return backtestBroker;
    }

    /**
     * Sets backtest broker.
     *
     * @param backtestBroker the backtest broker
     */
    public final void setBacktestBroker(BacktestBroker backtestBroker) {
        this.backtestBroker = backtestBroker;
    }
}
