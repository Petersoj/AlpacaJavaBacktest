package net.jacobpeterson.alpacajavabacktest;

import io.github.mainstringargs.alpaca.AlpacaAPI;
import io.github.mainstringargs.polygon.PolygonAPI;
import net.jacobpeterson.alpacajavabacktest.algorithm.TradingAlgorithm;
import net.jacobpeterson.alpacajavabacktest.algorithm.update.OtherUpdateType;
import net.jacobpeterson.alpacajavabacktest.algorithm.update.TickerUpdateType;
import net.jacobpeterson.alpacajavabacktest.broker.BacktestBroker;
import net.jacobpeterson.alpacajavabacktest.data.BacktestData;
import net.jacobpeterson.alpacajavabacktest.data.BacktestRecord;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * AlpacaJavaBacktest is a simple and fast Stock Trading Algorithm Backtesting Library for Java that uses
 * <a href="https://polygon.io/">Polygon</a> for historic quotes, trades, aggregates, and other data points to give
 * trading algorithm developers a more accurate understanding of what their algorithm might do in a live market. You
 * should have a live trading account with <a href="https://alpaca.markets">Alpaca</a> in order to get a free API key
 * for Polygon to use this backtesting library. This Library uses
 * <a href="https://github.com/mainstringargs/alpaca-java">alpaca-java</a> exclusively as it contains
 * a Polygon Java API and useful POJO classes.
 */
public class AlpacaJavaBacktest {

    private final BacktestData backtestData;
    private final BacktestBroker backtestBroker;
    private final BacktestRecord backtestRecord;
    private ZonedDateTime from;
    private ZonedDateTime to;
    private HashMap<String, TickerUpdateType[]> tickerUpdateTypes;
    private ArrayList<OtherUpdateType> otherUpdateTypes;

    /**
     * Instantiates a new Alpaca java backtest.
     */
    public AlpacaJavaBacktest() {
        this(new AlpacaAPI(), new PolygonAPI());
    }

    /**
     * Instantiates a new AlpacaJavaBacktest.
     *
     * @param alpacaAPI  the alpaca api
     * @param polygonAPI the polygon api
     */
    public AlpacaJavaBacktest(AlpacaAPI alpacaAPI, PolygonAPI polygonAPI) {
        this(new BacktestData(alpacaAPI, polygonAPI));
    }

    /**
     * Instantiates a new AlpacaJavaBacktest.
     *
     * @param backtestData the backtest data
     */
    public AlpacaJavaBacktest(BacktestData backtestData) {
        this(backtestData, new BacktestBroker(backtestData));
    }

    /**
     * Instantiates a new Alpaca java backtest.
     *
     * @param backtestData   the backtest data
     * @param backtestBroker the backtest broker
     */
    public AlpacaJavaBacktest(BacktestData backtestData, BacktestBroker backtestBroker) {
        this.backtestData = backtestData;
        this.backtestBroker = backtestBroker;
        this.backtestRecord = new BacktestRecord();

        this.tickerUpdateTypes = new HashMap<>();
        this.otherUpdateTypes = new ArrayList<>();
    }

    /**
     * Runs a backtest.
     *
     * @param tradingAlgorithm the trading algorithm
     */
    public void run(TradingAlgorithm tradingAlgorithm) {
        tradingAlgorithm.setBacktestData(backtestData);
        tradingAlgorithm.setBacktestBroker(backtestBroker);
        // TODO
    }

    /**
     * Add ticker update types.
     *
     * @param ticker            the ticker
     * @param tickerUpdateTypes the ticker update types
     */
    public void addTickerUpdateTypes(String ticker, TickerUpdateType... tickerUpdateTypes) {
        this.tickerUpdateTypes.put(ticker, tickerUpdateTypes);
    }

    /**
     * Add other update types.
     *
     * @param otherUpdateTypes the other update types
     */
    public void addOtherUpdateTypes(OtherUpdateType... otherUpdateTypes) {
        this.otherUpdateTypes.addAll(Arrays.asList(otherUpdateTypes));
    }

    /**
     * Gets backtest data.
     *
     * @return the backtest data
     */
    public BacktestData getBacktestData() {
        return backtestData;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public ZonedDateTime getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(ZonedDateTime from) {
        this.from = from;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public ZonedDateTime getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(ZonedDateTime to) {
        this.to = to;
    }

}
