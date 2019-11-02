package net.jacobpeterson.alpacajavabacktest;

import io.github.mainstringargs.polygon.PolygonAPI;
import net.jacobpeterson.alpacajavabacktest.data.BacktestData;

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

    private PolygonAPI polygonAPI;
    private BacktestData backtestData;

    /**
     * Instantiates a new AlpacaJavaBacktest and creates a new PolygonAPI instance.
     */
    public AlpacaJavaBacktest() {
        this.polygonAPI = new PolygonAPI();
        this.backtestData = new BacktestData(polygonAPI);
    }

    /**
     * Instantiates a new AlpacaJavaBacktest.
     *
     * @param polygonAPI the polygon api
     */
    public AlpacaJavaBacktest(PolygonAPI polygonAPI) {
        this.polygonAPI = polygonAPI;
        this.backtestData = new BacktestData(polygonAPI);
    }

    /**
     * Runs the backtest.
     */
    public void backtest() {
        // TODO
    }

    /**
     * Gets polygon api.
     *
     * @return the polygon api
     */
    public PolygonAPI getPolygonAPI() {
        return polygonAPI;
    }

    /**
     * Gets backtest data.
     *
     * @return the backtest data
     */
    public BacktestData getBacktestData() {
        return backtestData;
    }
}
