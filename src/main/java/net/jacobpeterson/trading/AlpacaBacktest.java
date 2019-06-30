package net.jacobpeterson.trading;

import net.jacobpeterson.trading.data.TickerData;

/**
 * AlpacaBacktest is a java stock trading algorithm backtester that uses <a href="https://polygon.io/">Polygon</a>
 * for historic quotes, trades, aggregates, and other data points. You should have a live trading account with
 * <a href="https://alpaca.markets">Alpaca</a> in order to get a free API key for Polygon to use this backtester.
 * This Library uses <a href="https://github.com/mainstringargs/alpaca-java">alpaca-java</a> exclusively as it contains
 * a PolygonAPI and useful POJO classes.
 */
public class AlpacaBacktest {

    private TickerData tickerData;

    public AlpacaBacktest() {

    }

    public void updatePortfolio() {

    }
}
